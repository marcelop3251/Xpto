package br.com.xpto.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) throws Exception {

		try {
			String pathMaquina = request.getServletContext().getRealPath("/" + baseFolder);
			String pathname = pathMaquina + "/" + file.getOriginalFilename();
			File dest = new File(pathname);
			if (!dest.exists())
				file.transferTo(dest);

			return pathname;
		} catch (IllegalStateException | IOException e) {
			throw new Exception("Não foi possível salvar o arquivo no servidor", e);
		}

	}
}
