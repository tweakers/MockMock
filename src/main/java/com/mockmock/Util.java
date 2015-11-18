package com.mockmock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Util
{
  	public String getFile(String fileName)
  	{
  		InputStream in;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;

		try
		{
			in = getClass().getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(in));

			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
  	}
}