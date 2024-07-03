Чтобы добавить в проект, написать в pom.xml:
```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>auditlib-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

<br/>

Логирование методов:

Чтобы включить логирование для метода, нужно добавить к нему аннотацию:
```java
@AuditLog(logLevel = Level.what_you_want_level)
```
Level - уровень логирования (из slf4j).

По умолчанию логирование для методов включено и если метод помечен аннотацией, 
то он логируется.

Параметры для настройки логирования методов:

auditlib.methodlog-enabled=true/false - включить/выключить функционал 
логирования методов (по умолчанию включен)

auditlib.methodlog.console-enabled=true/false - включить/выключить логирование в
консоль (по умолчанию включено)

auditlib.methodlog.file-enabled=true/false - включить/выключить логирование в файл
(по умолчанию выключено)

auditlib.methodlog.file-path=your_file_path - путь, куда сохранять файл с логами
(по умолчанию /logs/auditlib/methodlog). Название файл генерируется автоматически в 
формате yyyy-MM-dd.

<br/>

Логирование http запросов:

По умолчанию логирование для http запросов выключено.

Параметры для настройки логирования методов:

auditlib.httplog-enabled=true/false - включить/выключить функционал
логирования http запросов (по умолчанию выключен)

auditlib.httplog.console-enabled=true/false - включить/выключить логирование в
консоль (по умолчанию включено)

auditlib.httplog.file-enabled=true/false - включить/выключить логирование в файл
(по умолчанию выключено)

auditlib.httplog.file-path=your_file_path - путь, куда сохранять файл с логами
(по умолчанию /logs/auditlib/httplog). Название файл генерируется автоматически в
формате yyyy-MM-dd.