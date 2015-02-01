package ru.oxbao.speed_car;


public class RealtimeInputInterface extends InputOwnerInterface
{
    // Objects
    private InputAdapter m_inputAdapter;
    private TestExecutor m_ownerExecutor;

    public RealtimeInputInterface(ActivityTestExecutor m_ownerActivity, TestExecutor m_ownerExecutor)
    {
        m_inputAdapter = new InputAdapter(m_ownerActivity, m_ownerExecutor, this);
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
        m_inputAdapter.Stop();
    }

    @Override
    public String GetFileName()
    {
        return m_ownerExecutor.GetCheckedSpinner();
    }

    @Override
    public void ShowFailSensor()
    {
        m_ownerExecutor.ShowToast(TestExecutor.ToastMessage.failStartSensor);
    }

    public void Start()
    {
        m_inputAdapter.Start(InputAdapter.InputTypeEnum.sensors);
    }
}
