package ru.oxbao.sensor_test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;

public class StorageWriter
{
    private static final String DirectoryName = "/Accelerometer";
    private static final String FileExtension = ".txt";

    private String m_path;
    private String m_prefix;

    private File m_file;
    private FileOutputStream m_fos;

    StorageWriter(String externalStorageDirectory, String filenamePrefix)
    {
        m_path = GetDirectoryToSaveWhere(externalStorageDirectory);
        m_prefix = filenamePrefix;
    }

    public void StartNewFile()
    {
        String filename = GenerateNewFileName(m_prefix);

        try
        {
            m_file = new File(m_path, filename);
            m_fos = new FileOutputStream(m_file, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Write(String value)
    {
        value += "\n";
        try
        {
            m_fos.write(value.getBytes());
           // m_fos.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void Save()
    {
        try
        {
            m_fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String GetDirectoryToSaveWhere(String externalStorageDirectory)
    {
        String dir = externalStorageDirectory + DirectoryName;
        File folder = new File(dir);

        if(!folder.exists())
        {
            folder.mkdir();
        }

        return dir;
    }

    public static boolean SaveToFile(String value, String path, String prefix)
    {
        try
        {
            File file = new File(path, GenerateNewFileName(prefix));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(value.getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean SaveToFileTestData(TestData testData, String path, String prefix)
    {
        try
        {
            File file = new File(path, GenerateNewFileName(prefix));
            BufferedWriter bufferedFileWriter = new BufferedWriter(new FileWriter(file));

            String header = "XAxis YAxis ZAxis Time";
            bufferedFileWriter.write(header);
            bufferedFileWriter.newLine();

            for (int i = 0; i < testData.XAxis.length; i++){
                String tmp = testData.XAxis[i] + " " + testData.YAxis[i] + " " + testData.ZAxis[i] + " " + testData.TimeInMilliseconds[i];
                bufferedFileWriter.write(tmp);
                bufferedFileWriter.newLine();
            }
            bufferedFileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static String GenerateNewFileName(String prefix)
    {
        Calendar calendar = Calendar.getInstance();
        return prefix + '_' + calendar.get(Calendar.YEAR) + '_' + (calendar.get(Calendar.MONTH)+1) + '_' +
                calendar.get(Calendar.DAY_OF_MONTH) + '_' + calendar.get(Calendar.HOUR_OF_DAY) + '_' +
                calendar.get(Calendar.MINUTE) + '_' + calendar.get(Calendar.SECOND) + FileExtension;
    }
}
