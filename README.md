**Software Design**

**Tiny shell**

![Diagram](https://github.com/kostya93/software_design/blob/hw1/SoftwareDesignDiagram.png)

Внутри класса `Shell` крутится цикл, который читает строки из `stdin`. Каждая полученная строка сначала отдается объекту класса `Lexer`, который разбивает это строку на токены. После этого полученный массив токенов обрабатывается объектом класса `Preprocessor`, который осуществляет подстановку переменных и разворачивает присвоение вида `key=value` в `assign key value`, чтобы `assign` выглядел как обычная команда. После этого объект класса `Parser` преобразует массив токенов в массив команд с соответствующими данной команде аргументами. После чего `Shell` последовательно исполняет эти команды. Для передачи результатов между разными командами используется объект класса `Feature`, в котором каждая команда сохраняет результат своей работы, а также список ошибок, если таковые были.