package br.com.xpto.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.xpto.entidades.Capital;
import br.com.xpto.entidades.Cidade;
import br.com.xpto.entidades.Estado;
import br.com.xpto.entidades.Regiao;
import br.com.xpto.infra.FileSaver;
import br.com.xpto.services.CidadeService;
import br.com.xpto.services.EstadoService;
import br.com.xpto.services.RegiaoService;

@Controller
public class ArquivosController {

	@Autowired 
	private FileSaver fileSaver;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private RegiaoService regiaoService;
	
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(value ="/arquivo", method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile file) throws Exception{
		
		
		System.out.println(file.getOriginalFilename());
		
		fileSaver.write("Arquivos-csv", file);
		
		Optional<BufferedReader> bf = Optional.ofNullable(new BufferedReader(new InputStreamReader(file.getInputStream(),"utf-8")));
		
		BufferedReader bufferReader = bf.map( e -> e).orElseThrow(() -> new IllegalArgumentException("Nenhum arquivo foi recebido"));		
		
		
		String linha = null;
		List<String> linhas = new ArrayList<>();
			while(bufferReader.ready()){
				//pula o cabeçalho
				if(linha == null){
					 bufferReader.readLine();
				}
				linha = bufferReader.readLine();
				linhas.add(linha);
				//System.out.println("Adicionando linhas +" + linha);
			}
			linhas.forEach(e -> {
				//System.out.println("Processando  linha +" + e);
				String [] partesLinha = e.split(",");
				Estado estado = estadoService.finByNome(partesLinha[1]);
				if(estado == null){
					estado = new Estado();
					estado.setNome(partesLinha[1]);
					estadoService.salvar(estado);
				}
				
				Regiao regiao = regiaoService.findByMicroRegionAndMesoRegion(partesLinha[8],partesLinha[9]);
				if(regiao == null ){
					regiao = new Regiao();
					regiao.setMicroRegion(partesLinha[8]);
					regiao.setMesoRegion(partesLinha[9]);
					regiao = regiaoService.salvar(regiao);					
				}
				
				
				Cidade cidade = new Cidade();
				
				cidade.setIdIbge(Integer.parseInt(partesLinha[0]));
				cidade.setEstado(estado);
				cidade.setNome(partesLinha[2]);
				cidade.setCapital(partesLinha[3].equalsIgnoreCase("TRUE") ? Capital.SIM : Capital.NAO);
				cidade.setLongitude(Double.parseDouble(partesLinha[4]));
				cidade.setLatitude(Double.parseDouble(partesLinha[5]));
				cidade.setNomeAlternativo(partesLinha[6]);
				cidade.setRegiao(regiao);
				
				cidadeService.salvar(cidade);
			});
			
			return new ModelAndView("redirect: /mensagem");
	}
	
	@RequestMapping(value = "/arquivo" ,method = RequestMethod.GET)
	public ModelAndView arquivo(){
		return new ModelAndView("Arquivo");
	}
	
	@RequestMapping(value = "/mensagem" ,method = RequestMethod.GET)
	public ModelAndView mensagem(){
		return new ModelAndView("Mensagem");
	}
	
}
