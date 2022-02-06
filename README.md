### Hexlet tests and linter status:
[![Actions Status](https://github.com/MadMan2k/java-project-lvl5/workflows/hexlet-check/badge.svg)](https://github.com/MadMan2k/java-project-lvl5/actions)

[![Java CI](https://github.com/MadMan2k/java-project-lvl5/actions/workflows/main.yml/badge.svg)](https://github.com/MadMan2k/java-project-lvl5/actions/workflows/main.yml)

[![Maintainability](https://api.codeclimate.com/v1/badges/965d839e48dfbbb352a3/maintainability)](https://codeclimate.com/github/MadMan2k/java-project-lvl5/maintainability)

[![Test Coverage](https://api.codeclimate.com/v1/badges/965d839e48dfbbb352a3/test_coverage)](https://codeclimate.com/github/MadMan2k/java-project-lvl5/test_coverage)

# Tasker application

### About

Small Spring application for your team tasks management

It's fully functional and ready to use. All back-end part is made by me as a part of project and a front-end part is provided by [Hexlet](https://ru.hexlet.io/)

Feel free if you want to use my application! It's all explicitly in the public domain (I have to formally say that). You can grab a copy of the files to use and tweak as fits you best or use it as it is.

Check demo version on [Heroku](https://task3r-app.herokuapp.com/)

[Swagger documentation](https://task3r-app.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config) is also integrated in project for more details and better understanding

### How to use

Mekefile is present, so to run application in developer mod : 

```
make start
```
or
```
./gradlew bootRun --args='--spring.profiles.active=dev'
```

To tun application in production mod :

```
make start-prod
```
or
```
./gradlew bootRun --args='--spring.profiles.active=prod'
```

Chack application running at
```
http://localhost:5000/
```

Enjoy!