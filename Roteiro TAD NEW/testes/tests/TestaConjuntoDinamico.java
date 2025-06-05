package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tad.conjuntoDinamico.ConjuntoDinamicoIF;
import tad.conjuntoDinamico.ConjuntoDinamicoVazioException;
import tad.ElementoNaoEncontradoException;
import tad.conjuntoDinamico.MeuConjuntoDinamico;

public class TestaConjuntoDinamico {
	
	private ConjuntoDinamicoIF<Integer> cd = null;
	
	@BeforeEach
	public void iniciar() {
		cd = new MeuConjuntoDinamico();
	}

	@Test
	public void tamanhoTest() {
		assertEquals(0, cd.tamanho());
		cd.inserir(2);
		assertEquals(1, cd.tamanho());
		cd.inserir(2);
		assertEquals(2, cd.tamanho());
		cd.inserir(1);
		assertEquals(3, cd.tamanho());
		cd.inserir(5);
		assertEquals(4, cd.tamanho());
	}
	
	@Test
	public void inserirTeste() {
		assertEquals(0, cd.tamanho());
		cd.inserir(9);
		assertEquals(1, cd.tamanho());
		cd.inserir(4);
		assertEquals(2, cd.tamanho());
		cd.inserir(1);
		assertEquals(3, cd.tamanho());
		cd.inserir(1);
		assertEquals(4, cd.tamanho());
	}
	
	@Test
	public void removerTeste() throws Exception {
		assertEquals(0, cd.tamanho());
		cd.inserir(2);
		cd.inserir(2);
		cd.inserir(1);
		cd.inserir(5);
		assertEquals(2, cd.remover(2));
		assertEquals(3, cd.tamanho());
		
		assertEquals(1, cd.remover(1));
		assertEquals(2, cd.tamanho());
		
		assertEquals(5, cd.remover(5));
		assertEquals(1, cd.tamanho());
		
		assertEquals(2, cd.remover(2));
		assertEquals(0, cd.tamanho());
	}
	

    @Test
	public void removerFailTeste_ConjuntoVazio() {

		Exception e = assertThrows(ConjuntoDinamicoVazioException.class,
				() -> {cd.remover(3);});

		String expectedMessage = "Conjunto dinâmico Vazio!!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));


	}


	
	@Test
	public void removerFailTeste_ElementoNaoEncontrado() throws Exception {
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);

		Exception e = assertThrows(ElementoNaoEncontradoException.class,
				() -> {cd.remover(3);});

		String expectedMessage = "Elemento nao encontrado!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void buscarTeste() throws Exception {
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);
		assertEquals(10, cd.buscar(10));
	}
	
	@Test
	public void buscarFailTeste() throws Exception {
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);

		Exception e = assertThrows(ElementoNaoEncontradoException.class,
				() -> {cd.buscar(1);});

		String expectedMessage = "Elemento nao encontrado!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	public void minimumTeste() throws Exception {
		Exception e = assertThrows(ConjuntoDinamicoVazioException.class,
				() -> {cd.minimum();});

		String expectedMessage = "Conjunto dinâmico Vazio!!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);
		assertEquals(4,cd.minimum());
		
	}
	
	@Test
	public void maximumTest() throws Exception {
		Exception e = assertThrows(ConjuntoDinamicoVazioException.class,
				() -> {cd.maximum();});

		String expectedMessage = "Conjunto dinâmico Vazio!!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
		cd.inserir(5);
		cd.inserir(4);
		cd.inserir(10);
		cd.inserir(8);
		assertEquals(10,cd.maximum());
	}
	
	@Test
	public void sucessorTeste() throws Exception {
		Exception e = assertThrows(ConjuntoDinamicoVazioException.class,
				() -> {cd.sucessor(5);});

		String expectedMessage = "Conjunto dinâmico Vazio!!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);
		assertEquals(5,cd.sucessor(4));
		assertEquals(10,cd.sucessor(5));
		assertNull(cd.sucessor(8));
		assertEquals(8,cd.sucessor(10));
	}
	
	@Test
	public void predecessorTeste() throws Exception {

		Exception e = assertThrows(ConjuntoDinamicoVazioException.class,
				() -> {cd.predecessor(5);});

		String expectedMessage = "Conjunto dinâmico Vazio!!!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
		cd.inserir(4);
		cd.inserir(5);
		cd.inserir(10);
		cd.inserir(8);
		assertNull(cd.predecessor(4));
		assertEquals(5,cd.predecessor(10));
		assertEquals(4,cd.predecessor(5));
		assertEquals(10,cd.predecessor(8));
	}

	// ========== NOVOS TESTES ========== //

	@Test
	public void inserirNull_ShouldThrowException() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> cd.inserir(null));

		assertEquals("Elemento não pode ser null", e.getMessage());
	}

	@Test
	public void buscarNull_ShouldThrowException() {
		cd.inserir(1);

		Exception e = assertThrows(IllegalArgumentException.class,
				() -> cd.buscar(null));

		assertEquals("Elemento não pode ser null", e.getMessage());
	}

	@Test
	public void removerNull_ShouldThrowException() {
		cd.inserir(1);

		Exception e = assertThrows(IllegalArgumentException.class,
				() -> cd.remover(null));

		assertEquals("Elemento não pode ser null", e.getMessage());
	}

	@Test
	public void inserirRemocaoEmMassa_ShouldMaintainConsistency() throws ElementoNaoEncontradoException, ConjuntoDinamicoVazioException {
		// Teste de inserção em massa
		for (int i = 0; i < 1000; i++) {
			cd.inserir(i);
		}
		assertEquals(1000, cd.tamanho());

		// Verifica se todos os elementos foram inseridos
		for (int i = 0; i < 1000; i++) {
			assertEquals(i, cd.buscar(i));
		}

		// Teste de remoção em massa
		for (int i = 0; i < 1000; i++) {
			assertEquals(i, cd.remover(i));
		}
		assertEquals(0, cd.tamanho());

		// Verifica se todos os elementos foram removidos
		for (int i = 0; i < 1000; i++) {
			int finalI = i;
			assertThrows(ElementoNaoEncontradoException.class,
					() -> cd.buscar(finalI));
		}
	}

	@Test
	public void edgeCases_MinAndMaxIntegerValues() throws ElementoNaoEncontradoException, ConjuntoDinamicoVazioException {
		// Teste com valores extremos
		cd.inserir(Integer.MIN_VALUE);
		cd.inserir(Integer.MAX_VALUE);
		cd.inserir(0);

		assertEquals(3, cd.tamanho());

		// Verifica mínimo e máximo
		assertEquals(Integer.MIN_VALUE, cd.minimum());
		assertEquals(Integer.MAX_VALUE, cd.maximum());

		// Testa predecessores e sucessores
		assertNull(cd.predecessor(Integer.MIN_VALUE));
		assertEquals(Integer.MIN_VALUE, cd.predecessor(0));
		assertEquals(0, cd.predecessor(Integer.MAX_VALUE));

		assertEquals(0, cd.sucessor(Integer.MIN_VALUE));
		assertEquals(Integer.MAX_VALUE, cd.sucessor(0));
		assertNull(cd.sucessor(Integer.MAX_VALUE));

		// Remove valores extremos
		assertEquals(Integer.MIN_VALUE, cd.remover(Integer.MIN_VALUE));
		assertEquals(Integer.MAX_VALUE, cd.remover(Integer.MAX_VALUE));

		assertEquals(1, cd.tamanho());
	}

	@Test
	public void inserirElementosDuplicados_ShouldMaintainAllCopies() throws ElementoNaoEncontradoException, ConjuntoDinamicoVazioException {
		// Inserir o mesmo elemento múltiplas vezes
		for (int i = 0; i < 10; i++) {
			cd.inserir(42);
		}

		assertEquals(10, cd.tamanho());

		// Remover todas as cópias
		for (int i = 0; i < 10; i++) {
			assertEquals(42, cd.remover(42));
		}

		assertEquals(0, cd.tamanho());
		assertThrows(ConjuntoDinamicoVazioException.class, () -> cd.minimum());
	}

	@Test
	public void operationsOnEmptySet_ShouldThrowExceptions() {
		assertThrows(ConjuntoDinamicoVazioException.class, () -> cd.minimum());
		assertThrows(ConjuntoDinamicoVazioException.class, () -> cd.maximum());
		assertThrows(ConjuntoDinamicoVazioException.class,
				() -> cd.predecessor(1));
		assertThrows(ConjuntoDinamicoVazioException.class,
				() -> cd.sucessor(1));
		assertThrows(ConjuntoDinamicoVazioException.class,
				() -> cd.remover(1));
	}

}
