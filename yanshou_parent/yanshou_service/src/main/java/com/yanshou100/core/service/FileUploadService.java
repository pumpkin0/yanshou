package com.yanshou100.core.service;

import java.io.InputStream;

public interface FileUploadService {

	void uploadAndResponse(String filename, byte[] filebytes);

	void uploadAndResponse(String filename, InputStream inputStream);

}
