# Набор API автотестов для сайта <a target="_blank" href="https://reqres.in/">**Req | Res**</a>! 
<img src="img/screenshots/ReqRes.png" width="2000" height="100"  alt="Steam"/>


## ✔️ Реализованы четырнадцать тестов на разные API запросы :

|| REST                 | Discription         | Status |    | REST                 | Discription          |Status |
| :--| :------------------- | :-------------------| :---:  | :--| :------------------- | :------------------- | :---:
|✓| **[GET]** user list  | Получение списка пользователей  |200|✓| **[GET]** single user | Получение одного пользователя |200|
|✓| **[GET]** single user not found   | Пользователь не найден |404|✓| **[GET]** list resource | Получение списка ресурсов |200|
|✓| **[GET]** single resource    | Получение отдельного ресурса |200|✓| **[GET]** single resource not found | Отдельный ресурс не найден |404|
|✓| **[POST]** create user   | Создание пользователя |201|✓| **[PUT]** update user | Обновление пользователя |200|
|✓ |**[PATCH]** update user   | Обновление пользователя |200|✓| **[DELETE]** user | Удаление пользователя |204|
|✓ |**[POST]** register - successful   | Успешная регистрация |200|✓ |**[POST]** register - unsuccessful  | Неудачная регистрация |400|
| ✓|**[POST]** login - successful   | Успешный логин |200|✓| **[POST]** login - unsuccessful | Неудачный логин |400|








##  ⚒️  Используемые технологии и инструменты :


![Java](img/icons/Java.png)![Gradle](img/icons/Gradle.png)![JUnit5](img/icons/JUnit5.png)![Intelij_IDEA](img/icons/Intelij_IDEA.png)![Selenide](img/icons/Selenide.png)![Allure Report](img/icons/Allure_Report.png)![Github](img/icons/Github.png)![Jenkins](img/icons/Jenkins.png)![Telegram](img/icons/Telegram.png)


## <img src="img/icons/Jenkins.png" width="40" height="40"  alt="Jenkins"/></a> Jenkins <a target="_blank" href="https://jenkins.autotests.cloud/job/10-final_api_Kryuchkov_test/"> job </a>

![Jenkins](img/screenshots/jenkins_main.png)

![Jenkins](img/screenshots/jenkins_cfgs.png)



## Для локального запуска :
```bash
gradle clean test
```

## Для удаленного запуска с параметрами :
```bash
clean
test
```
___
## <img src="img/icons/Allure_Report.png" width="40" height="40"  alt="Allure"/></a> Отчет в <a target="_blank" href="https://jenkins.autotests.cloud/job/10-final_api_Kryuchkov_test/4/allure/">Allure report</a>

### Overview :
![allureMain](img/screenshots/allure_overview.png)

### Suites : 
![allureStats](img/screenshots/allure_suites.png)


### Graphs :
![allureGraphs](img/screenshots/allure_graphs.png)                                                   

![allureGraphs2](img/screenshots/allure_graphs_2.png)



## <img src="img/icons/Telegram.png" width="40" height="40"  alt="Telegram"/></a> Отчет в телеграмм о результатах тестов :

![Telegram](img/screenshots/telegram_bot.png)


## Остались вопросы? 
<a href="https://t.me/raboznik">
<img src="img/icons/Telegram.png" width="50" height="50"  alt="Telegram"/></a> 

<a href="mailto:raboznik@gmail.com">
<img src="img/icons/gmail.png" width="50" height="50"  alt="Gmail"/></a>  


