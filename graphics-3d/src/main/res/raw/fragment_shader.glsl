//устанавливает точность вычислений для float значений. Всего есть три режима: lowp, mediump, highp.
//Из названий понятна их точность. Но, разумеется, чем выше точность, тем ниже производительность.
precision mediump float;

//Переменная u_Color будет содержать в себе цвет.
//Она также имеет тип vec4, это вполне подходит под 4 компонента цвета RGBA.
//Значение в эту переменную мы будем задавать в нашем приложении.
//Слово uniform перед ней означает, что это значение будет всегда одинаково для всех фрагментов (точек),
//которые будут обработаны этим фрагментным шейдером.
uniform vec4 u_Color;

void main() {
    //Переменная gl_FragColor – это специальная переменная шейдера,
    //в которую мы должны поместить значение цвета для текущего фрагмента.
    //Напомню, что для каждой точки (фрагмента) треугольника система вызовет этот фрагментный шейдер,
    //и шейдер должен (в gl_FragColor) вернуть значение цвета, которое система использует для рисования точки.
    //
    //В gl_FragColor мы просто помещаем значение u_Color.
    //Т.е. фрагментный шейдер, так же, как и вершинный очень простой и транслирует данные дальше
    //без всяких изменений.
    gl_FragColor = u_Color;
}