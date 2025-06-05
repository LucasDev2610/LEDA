package tad.listasEncadeadas;

import tad.ElementoNaoEncontradoException;

public interface ListaDuplamenteEncadeadaIF<T extends Comparable<T>> extends ListaEncadeadaIF<T>{
	
	public void inserePrimeiro(T elemento);
	public NodoListaDuplamenteEncadeada<T> removeUltimo();
	public NodoListaDuplamenteEncadeada<T> removePrimeiro();
	public NodoListaDuplamenteEncadeada<T> search(T chave);
	public NodoListaDuplamenteEncadeada<T> remove(T chave) throws ElementoNaoEncontradoException;

}
