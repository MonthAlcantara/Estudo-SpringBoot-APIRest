package io.github.monthalcantara.mercadolivre.exception;

import java.util.Arrays;
import java.util.Collection;

public class ErroPadronizado {

    private Collection<String> mensagens;

    public ErroPadronizado(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }

    public ErroPadronizado(String reason) {
        this.mensagens = Arrays.asList(reason);
    }

    public Collection<String> getMensagens() {
        return mensagens;
    }
}
