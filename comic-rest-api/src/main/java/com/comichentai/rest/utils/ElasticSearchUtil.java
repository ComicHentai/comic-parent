package com.comichentai.rest.utils;

import java.io.*;

/**
 * Created by dintama on 16/5/1.
 */
public class ElasticSearchUtil {

    public  int execute(String command){
        String[] cmd = {"/bin/bash"};
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Output流用来向控制台写入命令
        OutputStream outputStream = process.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        try {
            bufferedWriter.write(command);
            bufferedWriter.flush();
            bufferedWriter.close();

            System.out.println(readCmd(process));

            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        int exitVal = process.exitValue();
        return exitVal;
    }

    public  String readCmd(Process process){
        StringBuffer stringBuffer = new StringBuffer();
        //input流用来读取cmd执行后的返回结果
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        try {
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return stringBuffer.toString().trim();
    }

}
