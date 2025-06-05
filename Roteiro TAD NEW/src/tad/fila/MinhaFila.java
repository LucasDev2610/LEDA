package tad.fila;

/**
 * Implementação de fila usando array fixo com estratégia circular
 * para gerenciamento eficiente dos apontadores de cabeça e cauda.
 * Esta implementação segue o princípio FIFO (First-In-First-Out).
 * 
 * @author Lucas Emanuel Ramos Silva
 */
public class MinhaFila implements FilaIF<Integer> {
    
    private int capacidade;
    private int tamanhoAtual = 0;
    private int cabeca = 0;
    private int cauda = 0;
    private Integer[] meusDados;

    /**
     * Constrói uma fila com capacidade especificada.
     * 
     * @param tamanhoInicial Capacidade inicial da fila
     */
    public MinhaFila(int tamanhoInicial) {
        this.capacidade = tamanhoInicial;
        meusDados = new Integer[capacidade];
    }
    
    /**
     * Constrói uma fila com capacidade padrão de 5 elementos.
     */
    public MinhaFila() {
        this(5);
    }

    /**
     * Adiciona um elemento no final da fila.
     * 
     * @param item Elemento a ser adicionado
     * @throws FilaCheiaException Se a fila estiver cheia
     */
    @Override
    public void enfileirar(Integer item) throws FilaCheiaException {
        if (isFull()) {
            throw new FilaCheiaException();
        }
        meusDados[cauda] = item;
        cauda = (cauda + 1) % capacidade;
        tamanhoAtual++;
    }

    /**
     * Remove e retorna o elemento do início da fila.
     * 
     * @return Elemento removido
     * @throws FilaVaziaException Se a fila estiver vazia
     */
    @Override
    public Integer desenfileirar() throws FilaVaziaException {
        if (isEmpty()) {
            throw new FilaVaziaException();
        }
        Integer item = meusDados[cabeca];
        meusDados[cabeca] = null;
        cabeca = (cabeca + 1) % capacidade;
        tamanhoAtual--;
        return item;
    }

    /**
     * Retorna o elemento no final da fila sem removê-lo.
     * 
     * @return Último elemento da fila ou null se vazia
     */
    @Override
    public Integer verificarCauda() {
        if (isEmpty()) {
            return null;
        }
        int posicaoCauda = (cauda == 0) ? capacidade - 1 : cauda - 1;
        return meusDados[posicaoCauda];
    }

    /**
     * Retorna o elemento no início da fila sem removê-lo.
     * 
     * @return Primeiro elemento da fila ou null se vazia
     */
    @Override
    public Integer verificarCabeca() {
        if (isEmpty()) {
            return null;
        }
        return meusDados[cabeca];
    }

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário
     */
    @Override
    public boolean isEmpty() {
        return tamanhoAtual == 0;
    }

    /**
     * Verifica se a fila está cheia.
     * 
     * @return true se a fila estiver cheia, false caso contrário
     */
    @Override
    public boolean isFull() {
        return tamanhoAtual == capacidade;
    }

    /**
     * Retorna a capacidade total da fila.
     * 
     * @return Capacidade máxima da fila
     */
    @Override
    public int capacidade() {
        return capacidade;
    }

    /**
     * Retorna o número atual de elementos na fila.
     * 
     * @return Quantidade de elementos na fila
     */
    @Override
    public int tamanho() {
        return tamanhoAtual;
    }
}