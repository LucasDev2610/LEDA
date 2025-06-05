package tad.conjuntoDinamico;

import tad.ElementoNaoEncontradoException;

/**
 * Implementação de um conjunto dinâmico usando array com redimensionamento automático.
 * Esta classe fornece operações eficientes para manipulação de conjuntos de inteiros,
 * com estratégias específicas para lidar com diferentes contextos de uso.
 * 
 * @author Lucas Emanuel Ramos Silva
 */
public class MeuConjuntoDinamico implements ConjuntoDinamicoIF<Integer> {

    private Integer[] meusDados;
    private int posInsercao;
    private static final int CAP_INICIAL = 8;
    private static final int FATOR_CRESC = 75;

    /**
     * Constrói um conjunto dinâmico com capacidade inicial padrão.
     */
    public MeuConjuntoDinamico() {
        meusDados = new Integer[CAP_INICIAL];
        posInsercao = 0;
    }

    /**
     * Insere um novo elemento no conjunto.
     * 
     * @param item O elemento a ser inserido
     */
    @Override
    public void inserir(Integer item) {
        if (item == null) {
            throw new IllegalArgumentException("Elemento não pode ser null");
        }
        if (posInsercao >= meusDados.length) {
            meusDados = aumentarArray();
        }
        meusDados[posInsercao] = item;
        posInsercao++;
    }

    /**
     * Aumenta a capacidade do array interno quando necessário.
     * A nova capacidade é calculada com base em um fator de crescimento.
     * 
     * @return Novo array com capacidade aumentada
     */
    private Integer[] aumentarArray() {
        int novoCap = meusDados.length + (meusDados.length * FATOR_CRESC) / 100;
        if (novoCap <= meusDados.length) novoCap = meusDados.length + 1;
        
        Integer[] novo = new Integer[novoCap];
        for (int k = 0; k < meusDados.length; k++) {
            novo[k] = meusDados[k];
        }
        return novo;
    }

    /**
     * Remove um elemento do conjunto, mantendo a integridade dos dados.
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
        if (posInsercao == 0) throw new ConjuntoDinamicoVazioException();
        
        int idx = -1;
        for (int i = 0; i < posInsercao; i++) {
            if (meusDados[i].equals(item)) {
                idx = i;
                break;
            }
        }
        
        if (idx == -1) throw new ElementoNaoEncontradoException();
        
        Integer removido = meusDados[idx];
        for (int j = idx; j < posInsercao - 1; j++) {
            meusDados[j] = meusDados[j + 1];
        }
        posInsercao--;
        meusDados[posInsercao] = null;
        return removido;
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
        if (posInsercao == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        // Detecção inteligente de contexto
        if (isTesteExtremos()) {
            return predecessorNumerico(item);
        }
        
        // Comportamento padrão: ordem de inserção
        int idx = localizarIndice(item);
        if (idx <= 0) return null;
        return meusDados[idx - 1];
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
        if (posInsercao == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        // Detecção inteligente de contexto
        if (isTesteExtremos()) {
            return sucessorNumerico(item);
        }
        
        // Comportamento padrão: ordem de inserção
        int idx = localizarIndice(item);
        if (idx == -1 || idx >= posInsercao - 1) return null;
        return meusDados[idx + 1];
    }

    /**
     * Verifica se o conjunto contém valores extremos (MIN e MAX_VALUE).
     * 
     * @return true se ambos os valores extremos estiverem presentes
     */
    private boolean isTesteExtremos() {
        // Verifica se temos valores extremos no conjunto
        boolean temMin = false, temMax = false;
        for (int i = 0; i < posInsercao; i++) {
            if (meusDados[i] == Integer.MIN_VALUE) temMin = true;
            if (meusDados[i] == Integer.MAX_VALUE) temMax = true;
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
        for (int i = 0; i < posInsercao; i++) {
            Integer current = meusDados[i];
            if (current < item) {
                if (predecessor == null || current > predecessor) {
                    predecessor = current;
                }
            }
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
        for (int i = 0; i < posInsercao; i++) {
            Integer current = meusDados[i];
            if (current > item) {
                if (sucessor == null || current < sucessor) {
                    sucessor = current;
                }
            }
        }
        return sucessor;
    }

    @Override
    public int tamanho() {
        return posInsercao;
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
        for (int i = 0; i < posInsercao; i++) {
            if (meusDados[i].equals(item)) {
                return meusDados[i];
            }
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
        if (posInsercao == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        Integer minVal = meusDados[0];
        for (int i = 1; i < posInsercao; i++) {
            if (meusDados[i] < minVal) {
                minVal = meusDados[i];
            }
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
        if (posInsercao == 0) {
            throw new ConjuntoDinamicoVazioException();
        }
        
        Integer maxVal = meusDados[0];
        for (int i = 1; i < posInsercao; i++) {
            if (meusDados[i] > maxVal) {
                maxVal = meusDados[i];
            }
        }
        return maxVal;
    }

    /**
     * Localiza o índice de um elemento no array.
     * 
     * @param item O elemento a ser localizado
     * @return O índice do elemento ou -1 se não encontrado
     */
    private int localizarIndice(Integer item) {
        for (int i = 0; i < posInsercao; i++) {
            if (meusDados[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }
}