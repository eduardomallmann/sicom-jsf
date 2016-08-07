/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe BaseController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe por implementar os serviços base de Controllers do projeto.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class BaseController {

    private static final String BUNDLE = "org.sigmo.sicom.resources.ApplicationResources";

    private String bundle;
    private Integer idSelected;

    /**
     * Adiciona mensagem a ser exibida na tela.
     *
     * @param severity   nível de severidade da mensagem.
     * @param keyMessage chave de acesso ao texto no properties.
     */
    public void addMessage(final FacesMessage.Severity severity, final String keyMessage) {
        addMessage(severity, null, keyMessage);
    }

    /**
     * Adiciona mensagem a ser exibida na tela.
     *
     * @param severity    nível de severidade da mensagem.
     * @param idComponent componente referente a mensagem.
     * @param keyMessage  chave de acesso ao texto no properties.
     */
    public void addMessage(final FacesMessage.Severity severity, final String idComponent, final String keyMessage) {
        String msg = getBundleMessage(keyMessage);
        getContext().addMessage(idComponent, new FacesMessage(severity, msg, msg));
    }

    /**
     * Adiciona mensagem a ser exibida na tela.
     *
     * @param severity nível de severidade da mensagem.
     * @param msg      mensagem de texto a ser exibida.
     */
    public void addFullMessage(final FacesMessage.Severity severity, final String msg) {
        getContext().addMessage(null, new FacesMessage(severity, msg, msg));
    }

    /**
     * Busca a mensagem dentro do properties.
     *
     * @param key chave de acesso ao texto no properties.
     *
     * @return mensagem de texto a ser apresentada.
     */
    protected String getBundleMessage(final String key) {
        ResourceBundle bundleResource = ResourceBundle.getBundle(BUNDLE,
                                                                 getContext().getViewRoot().getLocale());

        return bundleResource.getString(key);
    }

    /**
     * Método GET configurado para instanciar o objeto "bundle" quando nulo.
     *
     * @return objeto "bundle" instanciado.
     */
    public String getBundle() {
        if (this.bundle == null) {
            this.bundle = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("app.resources");
        }
        return this.bundle;
    }

    /**
     * Método GET configurado para pegar o contexto informado.
     *
     * @return contexto informado
     */
    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Recebe o parâmetro do browser e instância o atributo requestParams.
     *
     * @param paramBrowser parâmetro repassado pelo browser
     *
     * @return atributo instanciado
     */
    protected String getRequestParams(final String paramBrowser) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> param = context.getExternalContext().getRequestParameterMap();
        return param.get(paramBrowser);
    }

    public Integer getIdSelected() {
        return this.idSelected;
    }

    public void setIdSelected(final Integer idSelected) {
        this.idSelected = idSelected;
    }

}
