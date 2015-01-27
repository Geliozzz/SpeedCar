package ru.oxbao.speed_car;


public class RealtimeInput extends InputInterfaceAdapter
{
    // Objects
    private InputInterface m_inputInterface;
    private TestExecutor m_ownerExecutor;

    public RealtimeInput(ActivityTestExecutor m_ownerActivity, TestExecutor m_ownerExecutor)
    {
        m_inputInterface = new InputInterface(m_ownerActivity, m_ownerExecutor, this);
        this.m_ownerExecutor = m_ownerExecutor;
    }

    @Override
    public void Amass(double XAxis, double YAxis, double ZAxis, long time)
    {
        m_ownerExecutor.ProcessSample(XAxis, YAxis, ZAxis, time);
    }

    @Override
    public void Stop()
    {
        m_inputInterface.Stop();
    }

    public void Start()
    {
        m_inputInterface.Start(InputInterface.InputTypeEnum.sensors);
    }
}
