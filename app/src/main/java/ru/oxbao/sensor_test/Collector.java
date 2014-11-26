package ru.oxbao.sensor_test;


public class Collector {
    private static InputInterface m_inputInterface;
    private static int count;
    public static void Start(boolean input){
        m_inputInterface = new InputInterface();
        if (input){
            m_inputInterface.Start(InputInterface.InputTypeEnum.sensors);
        } else{

        }


    }

    public static void Collect(){
        //Заполняем тестДата
        count++;
        //после заполнения стоп
        m_inputInterface.Stop();
    }
}
