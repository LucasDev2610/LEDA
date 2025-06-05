package tad.fila;

/**
 * Implementação de fila usando lista encadeada.
 * Esta implementação não possui limitação prática de tamanho
 * e segue o princípio FIFO (First-In-First-Out).
 * 
 * @author Lucas Emanuel Ramos Silva
 */
public class MinhaFilaEncadeada implements FilaIF<Integer> {

    private static class Nodo {
        Integer valor;
        Nodo proximo;
        
        Nodo(Integer valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }
    
    private Nodo cabeca = null;
    private Nodo cauda = null;
    private int tamanho = 0;

    /**
     * Adiciona um elemento no final da fila.
     * 
     * @param item Elemento a ser adicionado
     * @throws FilaCheiaException Apenas por compatibilidade com a interface
     */
    @Override
    public void enfileirar(Integer item) throws FilaCheiaException {
        Nodo novo = new Nodo(item);
        if (isEmpty()) {
            cabeca = novo;
        } else {
            cauda.proximo = novo;
        }
        cauda = novo;
        tamanho++;
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
        Integer valor = cabeca.valor;
        cabeca = cabeca.proximo;
        if (cabeca == null) {
            cauda = null;
        }
        tamanho--;
        return valor;
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
        return cauda.valor;
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
        return cabeca.valor;
    }

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário
     */
    @Override
    public boolean isEmpty() {
        return cabeca == null;
    }

    /**
     * Verifica se a fila está cheia.
     * 
     * @return false sempre, pois a fila encadeada não tem capacidade máxima
     */
    @Override
    public boolean isFull() {
        return false;
    }

    /**
     * Retorna a capacidade teórica da fila.
     * 
     * @return Integer.MAX_VALUE (valor máximo para inteiro)
     */
    @Override
    public int capacidade() {
        return Integer.MAX_VALUE;
    }

    /**
     * Retorna o número atual de elementos na fila.
     * 
     * @return Quantidade de elementos na fila
     */
    @Override
    public int tamanho() {
        return tamanho;
    }
}