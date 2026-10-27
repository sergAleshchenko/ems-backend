# Локальный EMS в Rancher Desktop

Адрес: http://ems.localhost (HTTP, без port-forward).

Репозитории:
- /Users/sergei_aleshchenko/Documents/GitHub/ems-backend
- /Users/sergei_aleshchenko/Documents/GitHub/ems-frontend

Namespace: ems. Три Deployment: ems-frontend, ems-backend, ems-postgres.
Образы приложений: ems-frontend:local-3 и ems-backend:local-1.
База: postgres:16-alpine; PVC ems-postgres-data, 1 GiB.
Пароль сгенерирован отдельно и хранится в Kubernetes Secret ems-db.
Манифест не содержит пароля. При повторном создании namespace необходимо
сначала создать Secret ems-db с ключами POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB.

## Сборка и запуск

В ems-backend:
```sh
mvn package -DskipTests
docker build -t ems-backend:local-1 .
docker build -t ems-frontend:local-3 ../ems-frontend
kubectl --context rancher-desktop apply -f deploy/kubernetes.yaml
kubectl --context rancher-desktop -n ems rollout restart deployment/ems-backend deployment/ems-frontend
kubectl --context rancher-desktop -n ems get pods
```

Для изменения кода предпочтительнее новый тег образа и изменение image в манифесте.
Образы локальные: imagePullPolicy Never; Docker context должен быть rancher-desktop.
Обычные контекстные Maven-тесты требуют PostgreSQL; при первом развёртывании
вместо них проверены реальные создание и редактирование через браузер.

## Изменения для запуска

- Java runtime заменён на Temurin 17 с ARM64-поддержкой.
- React собирается через npm ci и Vite в Node 22, раздаётся nginx.
- Фронтенд обращается к /api/employees; nginx проксирует /api/ в ems-backend:8080.
- Настройки PostgreSQL переопределены переменными окружения из Secret.
- Форма добавления не запрашивает сотрудника с id=undefined.
- API, PostgreSQL и nginx имеют readiness probes.
- Traefik маршрутизирует ems.localhost во frontend Service.

Проверено: frontend build, backend Maven package, создание и изменение сотрудника
в браузере, чтение записи из PostgreSQL. Оставлена запись Demo / Employee Updated.
Прежнее демо Hello Club не изменено.

Удаление namespace ems удалит PVC; не выполнять, если нужны сохранённые данные.
