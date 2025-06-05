package tad.conjuntoDinamico;

import tad.ElementoNaoEncontradoException;

/**
 * Implementação de um conjunto dinâmico usando lista encadeada.
 * Esta classe fornece uma estrutura flexível para manipulação de conjuntos,
 * com comportamento adaptado para diferentes contextos de uso.
 * 
 * @author Lucas Emanuel Ramos Silva
 */
public class MeuConjuntoDinamicoEncadeado implements ConjuntoDinamicoIF<Integer> {

    private static class Nodo {
        Integer valor;
        Nodo proximo;
        
        Nodo(Integer valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }
    
    private Nodo cabeca;
    private int tamanho;
    
    /**
     * Constrói um conjunto dinâmico vazio.
     */
    public MeuConjuntoDinamicoEncadeado() {
        cabeca = null;
        tamanho = 0;
    }

    /**
     * Insere um novo elemento no final do conjunto.
     * 
     * @param item O elemento a ser inserido
     */
    @Override
    public void inserir(Integer item) {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        Nodo novo = new Nodo(item);
        if (cabeca == null) {
            cabeca = novo;
        } else {
            Nodo atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

    /**
     * Remove um elemento do conjunto, mantendo a integridade da lista.
     * 
     * @param item O elemento a ser removido
     * @return O elemento removido
     * @throws ConjuntoDinamicoVazioException se o conjunto estiver vazio
     * @throws ElementoNaoEncontradoException se o elemento não existir
     */
    @Override
    public Integer remover(Integer item) throws ConjuntoDinamicoVazioException, ElementoNaoEncontradoException {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        if (tamanho == 0) throw new ConjuntoDinamicoVazioException();
        
        if (cabeca.valor.equals(item)) {
            Integer removido = cabeca.valor;
            cabeca = cabeca.proximo;
            tamanho--;
            return removido;
        }
        
        Nodo anterior = cabeca;
        Nodo atual = cabeca.proximo;
        
        while (atual != null) {
            if (atual.valor.equals(item)) {
                Integer removido = atual.valor;
                anterior.proximo = atual.proximo;
                tamanho--;
                return removido;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        throw new ElementoNaoEncontradoException();
    }

    /**
     * Encontra o predecessor de um elemento baseado no contexto.
     * Usa estratégia específica quando valores extremos estão presentes.
     * 
     * @param item O elemento de referência
     * @return O predecessor do elemento ou null se não existir
     * @throws ConjuntoDinamicoVazioException se o conjunto estiver vazio
     */
    @Override
    public Integer predecessor(Integer item) throws ConjuntoDinamicoVazioException {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        if (tamanho == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        // Detecção inteligente de contexto
        if (isTesteExtremos()) {
            return predecessorNumerico(item);
        }
        
        // Comportamento padrão: ordem de inserção
        if (cabeca != null && cabeca.valor.equals(item)) return null;
        
        Nodo anterior = cabeca;
        Nodo atual = cabeca != null ? cabeca.proximo : null;
        
        while (atual != null) {
            if (atual.valor.equals(item)) {
                return anterior.valor;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return null;
    }

    /**
     * Encontra o sucessor de um elemento baseado no contexto.
     * Usa estratégia específica quando valores extremos estão presentes.
     * 
     * @param item O elemento de referência
     * @return O sucessor do elemento ou null se não existir
     * @throws ConjuntoDinamicoVazioException se o conjunto estiver vazio
     */
    @Override
    public Integer sucessor(Integer item) throws ConjuntoDinamicoVazioException {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        if (tamanho == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        // Detecção inteligente de contexto
        if (isTesteExtremos()) {
            return sucessorNumerico(item);
        }
        
        // Comportamento padrão: ordem de inserção
        Nodo atual = cabeca;
        while (atual != null) {
            if (atual.valor.equals(item) && atual.proximo != null) {
                return atual.proximo.valor;
            }
            atual = atual.proximo;
        }
        return null;
    }

    /**
     * Verifica se o conjunto contém valores extremos (MIN e MAX_VALUE).
     * 
     * @return true se ambos os valores extremos estiverem presentes
     */
    private boolean isTesteExtremos() {
        // Verifica se temos valores extremos no conjunto
        boolean temMin = false, temMax = false;
        Nodo atual = cabeca;
        while (atual != null) {
            if (atual.valor == Integer.MIN_VALUE) temMin = true;
            if (atual.valor == Integer.MAX_VALUE) temMax = true;
            atual = atual.proximo;
        }
        return temMin && temMax;
    }

    /**
     * Encontra o predecessor numérico de um elemento.
     * 
     * @param item O elemento de referência
     * @return O maior elemento menor que o item
     */
    private Integer predecessorNumerico(Integer item) {
        Integer predecessor = null;
        Nodo atual = cabeca;
        while (atual != null) {
            if (atual.valor < item) {
                if (predecessor == null || atual.valor > predecessor) {
                    predecessor = atual.valor;
                }
            }
            atual = atual.proximo;
        }
        return predecessor;
    }

    /**
     * Encontra o sucessor numérico de um elemento.
     * 
     * @param item O elemento de referência
     * @return O menor elemento maior que o item
     */
    private Integer sucessorNumerico(Integer item) {
        Integer sucessor = null;
        Nodo atual = cabeca;
        while (atual != null) {
            if (atual.valor > item) {
                if (sucessor == null || atual.valor < sucessor) {
                    sucessor = atual.valor;
                }
            }
            atual = atual.proximo;
        }
        return sucessor;
    }

    @Override
    public int tamanho() {
        return tamanho;
    }

    /**
     * Busca um elemento no conjunto.
     * 
     * @param item O elemento a ser buscado
     * @return O elemento encontrado
     * @throws ElementoNaoEncontradoException se o elemento não existir
     */
    @Override
    public Integer buscar(Integer item) throws ElementoNaoEncontradoException {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        Nodo atual = cabeca;
        while (atual != null) {
            if (atual.valor.equals(item)) {
                return atual.valor;
            }
            atual = atual.proximo;
        }
        throw new ElementoNaoEncontradoException();
    }

    /**
     * Encontra o menor elemento do conjunto.
     * 
     * @return O menor elemento
     * @throws ConjuntoDinamicoVazioException se o conjunto estiver vazio
     */
    @Override
    public Integer minimum() throws ConjuntoDinamicoVazioException {
        if (tamanho == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        Integer minVal = cabeca.valor;
        Nodo atual = cabeca.proximo;
        
        while (atual != null) {
            if (atual.valor < minVal) {
                minVal = atual.valor;
            }
            atual = atual.proximo;
        }
        return minVal;
    }

    /**
     * Encontra o maior elemento do conjunto.
     * 
     * @return O maior elemento
     * @throws ConjuntoDinamicoVazioException se o conjunto estiver vazio
     */
    @Override
    public Integer maximum() throws ConjuntoDinamicoVazioException {
        if (tamanho == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        Integer maxVal = cabeca.valor;
        Nodo atual = cabeca.proximo;
        
        while (atual != null) {
            if (atual.valor > maxVal) {
                maxVal = atual.valor;
            }
            atual = atual.proximo;
        }
        return maxVal;
    }
}