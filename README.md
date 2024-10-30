# CRM-system
## 1. Определение требований

   - ### Аутентификация и авторизация:
     - Роли пользователей, как «Менеджер» (может создавать и редактировать проекты и задачи) и «Исполнитель» (может видеть и обновлять только назначенные задачи).

   - ### CRUD операции для проектов и задач:
     - Создание, чтение, обновление и удаление проектов и задач.

   - ### Управление статусами и приоритетами задач.

   - ### Система уведомлений:
     - Отправка уведомлений в случае изменения статуса задачи (использовать многопоточность для обработки и отправки уведомлений).

   - ### Отчетность:
     - Генерация отчетов по статусу проекта, выполненным задачам, эффективности команды.

   - ### Многопоточность:
     - Использовать для оптимизации отправки уведомлений и выполнения фоновых операций.


## 2. Архитектура и дизайн проекта

   ### Общая архитектура: MVC

   - #### Model: 
      Содержит бизнес-логику и взаимодействие с базой данных.

   - #### View: 
      Интерфейс для представления данных.

   - #### Controller: 
      Обрабатывает запросы от пользователя и взаимодействует с Model для выполнения бизнес-логики.

   ### Используемые паттерны проектирования:

   - #### DAO (Data Access Object):
      Для управления доступом к данным (Проекты, Задачи, Пользователи).

   - #### Factory: 
      Для создания экземпляров задач и проектов.

   - #### Observer:
      Для реализации системы уведомлений (подписка на обновления задач).

## 3. Подробный план разработки компонентов

   ### 3.1 Модель (Model)

   #### 1. Классы сущностей (Entity)

   - ##### User: 
      Имеет свойства id, username, password, role.

   - ##### Project: 
      Имеет свойства id, name, description, status.

   - ##### Task: 
      Имеет свойства id, title, description, status, priority, assignedUser, project.

   #### 2. Классы DAO

   - ##### UserDAO: 
      Методы для управления данными пользователя (createUser, getUserById, updateUser, deleteUser, findByUsername).

   - ##### ProjectDAO:
      Методы для управления данными проекта (createProject, getProjectById, updateProject, deleteProject, getAllProjects).

   - ##### TaskDAO:
      Методы для управления данными задачи (createTask, getTaskById, updateTask, deleteTask, getTasksByProject).

   #### 3. Repository:
   Использование Spring data-jpa для создания репозитория.

   ### 3.2 Контроллеры (Controllers)
   
   #### 1. AuthenticationController:
   - Методы для аутентификации пользователя и проверки ролей.
   - Проверка прав доступа для выполнения определенных операций.

   #### 2. ProjectController:
   - Методы для создания, обновления, удаления и получения информации о проекте.
   - Взаимодействие с ProjectDAO для CRUD операций с проектами.

   #### 3. TaskController:
   - Методы для создания, назначения, обновления и получения задач.
   - Логика для управления статусом и приоритетами задач.

   #### 4. NotificationController:
   - Подписывает пользователей на уведомления о задачах, используя паттерн Observer.
   - Использует многопоточность для асинхронной отправки уведомлений (например, через email или сообщения в системе).

   ### 3.3 Представление (View)

   #### 1. Формы для CRUD операций

   - ##### User Login Page: 
      Форма для аутентификации.

   - ##### Project Management Page:
      Форма для создания и редактирования проектов.

   - ##### Task Management Page: 
      Форма для управления задачами и статусами.

   #### 2. Отображение данных
   - Списки проектов и задач с возможностью фильтрации и сортировки.
   - Отчеты по статусу задач и проектов.

## 4. Реализация многопоточности
   #### 1. Система уведомлений
   - Создать отдельный поток для отправки уведомлений об изменении статуса задачи, используя ExecutorService.
   - Потоки для отправки уведомлений должны выполняться асинхронно, чтобы не блокировать основной поток выполнения приложения.

   #### 2. Фоновые задачи
   - Планирование фоновых задач (например, ежедневный отчет о статусе проектов), используя ScheduledExecutorService.

## 5. Тестирование и отладка
   #### 1. Тесты для DAO и контроллеров

   - Юнит-тесты для каждого DAO, проверяющие основные CRUD операции.
   - Юнит-тесты для контроллеров, проверяющие бизнес-логику (например, назначение задач).

   #### 2. Мок-тестирование
   - Использовать Mockito для мокирования зависимостей (например, базы данных) при тестировании логики.

   #### 3. Многопоточные тесты
   - Тестирование многопоточности: проверить, что отправка уведомлений происходит асинхронно и корректно обрабатывает конкурентные запросы.

## 6. Развертывание и доработка
   - Подготовить документацию для развертывания и настройки системы.
   - Настроить конфигурационные файлы для базы данных, ролей пользователей, доступа и многопоточности.