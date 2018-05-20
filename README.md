# Логистика

Для создания таблиц и заполенния из данными выполнить mvn test.

Отчеты строятся непосредственно в тестах и выводятся в консоль.

### Схема БД
![Схема БД](https://github.com/antkuznetsov/logistics/raw/master/bd_schema.png)

### Пример отчётов

ЛУЧШИЕ КЛИЕНТЫ
* по заключенным договорам
    * ООО Бестранк (договоров на сумму: 50500)
    * ООО Ай-Теко (договоров на сумму: 10000)
* по фактическим платежам
    * ООО Бестранк (платежей на сумму: 28200)
    * ООО Ай-Теко (платежей на сумму: 3300)

ДОЛЖНИКИ
* ООО Ай-Теко (долг: 6700)

ПОПУЛЯРНЫЙ ГОРОД
* Санкт-Петербург (доставок: 4)

ДОЛГИЕ НАПРАВЛЕНИЯ
* по среднему ожиданию
    * Санкт-Петербург (дней ожидания: 7)
    * Иннополис (дней ожидания: 3)
* по суммарному ожиданию
    * Санкт-Петербург (дней ожидания: 21)
    * Иннополис (дней ожидания: 6)

ПОПУЛЯРНЫЙ ПРОДУКТ
* Nokia (продано штук: 4)
* iPhone (продано штук: 3)