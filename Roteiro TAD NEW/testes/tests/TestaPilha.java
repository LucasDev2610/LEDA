package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tad.pilha.MinhaPilha;
import tad.pilha.PilhaCheiaException;
import tad.pilha.PilhaIF;
import tad.pilha.PilhaVaziaException;

public class TestaPilha {
	
	protected PilhaIF<Integer> pilha = null;
	
	@BeforeEach
	public void iniciar() {
		pilha = new MinhaPilha();
	}
	
	@Test
	public void empilharTest() {
		try {
			pilha.empilhar(3);
		
			assertEquals(3, pilha.topo());
			pilha.empilhar(5);
			assertEquals(5, pilha.topo());
			pilha.empilhar(7);
			assertEquals(7, pilha.topo());
			pilha.empilhar(4);
			assertEquals(4, pilha.topo());
			pilha.empilhar(2);
			assertEquals(2, pilha.topo());
		} catch (PilhaCheiaException e) {
			fail("empilharTest está estourando a pilha erroneamente");
		}
	}
	
	@Test
	public void topoTest() {
		assertNull(pilha.topo());
		try {
			pilha.empilhar(3);
			assertEquals(3, pilha.topo());
			pilha.empilhar(5);
			assertEquals(5, pilha.topo());
			pilha.empilhar(7);
			assertEquals(7, pilha.topo());
			pilha.empilhar(4);
			assertEquals(4, pilha.topo());
			pilha.empilhar(2);
			assertEquals(2, pilha.topo());
		} catch (PilhaCheiaException e) {
			fail(" está estourando a pilha erroneamente");
		}
		
	}
	
	@Test
	public void desempilharTest() throws PilhaCheiaException, PilhaVaziaException {
		Exception e = assertThrows(PilhaVaziaException.class,
				() -> {pilha.desempilhar();});
		String expectedMessage = "pilha vazia!!";
		String actualMessage = e.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));


		pilha.empilhar(3);
		assertEquals(3, pilha.topo());
		pilha.empilhar(5);
		assertEquals(5, pilha.topo());
		pilha.empilhar(7);
		assertEquals(7, pilha.topo());
		pilha.empilhar(4);
		assertEquals(4, pilha.topo());
		pilha.empilhar(2);

		assertEquals(2, pilha.topo());
		assertEquals(2, pilha.desempilhar());
		assertEquals(4, pilha.topo());

		assertEquals(4, pilha.desempilhar());
		assertEquals(7, pilha.topo());

		assertEquals(7, pilha.desempilhar());
		assertEquals(5, pilha.topo());

		assertEquals(5, pilha.desempilhar());
		assertEquals(3, pilha.topo());

		assertEquals(3, pilha.topo());

	}
	
	@Test
	public void isEmptyTest() {
		assertTrue(pilha.isEmpty());
		try { 
			pilha.empilhar(3);
			assertEquals(3, pilha.topo());
			assertFalse(pilha.isEmpty());
			pilha.desempilhar();
			assertTrue(pilha.isEmpty());
			pilha.empilhar(4);
			pilha.empilhar(6);
			assertFalse(pilha.isEmpty());
		} catch (PilhaCheiaException e) {
			fail(" está estourando a pilha erroneamente");
		} catch (PilhaVaziaException e) {
			fail(" está esvaziando a pilha erroneamente");
		}
	}
	
	@Test
	public void pilhaVaziaTest() {
		assertThrows(PilhaVaziaException.class, () -> {
			pilha.empilhar(3);
			pilha.empilhar(2);
			pilha.empilhar(10);
			pilha.desempilhar();
			pilha.desempilhar();
			pilha.desempilhar();
			pilha.desempilhar();
	    });
	}
	
	@Test
	public void pilhaCheiaTest() {
		assertThrows(PilhaCheiaException.class, () -> {
			pilha.empilhar(3);
			pilha.empilhar(2);
			pilha.empilhar(10);
			pilha.empilhar(3);
			pilha.empilhar(2);
			pilha.empilhar(10);
	    });
	}

	@Test
	public void multitop_QuandoSolicitadoMaisElementosQuePilha_DeveRetornarTodosElementos() throws PilhaCheiaException, PilhaVaziaException {
		pilha.empilhar(3);
		pilha.empilhar(2);
		pilha.empilhar(10);

		PilhaIF<Integer> esperado = new MinhaPilha();
		esperado.empilhar(10);
		esperado.empilhar(2);
		esperado.empilhar(3);

		assertEquals(esperado, pilha.multitop(5), "Deve retornar todos elementos quando solicitado mais que o tamanho");
	}

	@Test
	public void multitop_QuandoParametroZeroOuNegativo_DeveLancarExcecao() {
		assertThrows(IllegalArgumentException.class, () -> pilha.multitop(0));
		assertThrows(IllegalArgumentException.class, () -> pilha.multitop(-1));
	}

	// Testes de capacidade
	@Test
	public void capacidadePadrao_DeveSerCinco() {
		assertEquals(5, pilha.capacidade());
	}

	@Test
	public void capacidadeCustomizada_DeveRespeitarTamanhoInformado() {
		PilhaIF<Integer> pilhaCustom = new MinhaPilha(100);
		assertEquals(100, pilhaCustom.capacidade());
	}

	// Testes de tamanho
	@Test
	public void tamanho_DeveRefletirQuantidadeElementos() throws PilhaCheiaException {
		assertEquals(0, pilha.tamanho());
		pilha.empilhar(1);
		assertEquals(1, pilha.tamanho());
		pilha.empilhar(2);
		assertEquals(2, pilha.tamanho());
	}

	// Testes com valores extremos
	@Test
	public void empilharValoresExtremos_DeveManterOrdemCorreta() throws PilhaCheiaException, PilhaVaziaException {
		pilha.empilhar(Integer.MIN_VALUE);
		pilha.empilhar(Integer.MAX_VALUE);

		assertEquals(Integer.MAX_VALUE, pilha.topo());
		assertEquals(Integer.MAX_VALUE, pilha.desempilhar());
		assertEquals(Integer.MIN_VALUE, pilha.topo());
	}

	// Teste de comportamento LIFO
	@Test
	public void comportamentoPilha_DeveSeguirOrdemLIFO() throws PilhaCheiaException, PilhaVaziaException {
		pilha.empilhar(10);
		pilha.empilhar(20);
		pilha.empilhar(30);

		assertEquals(30, pilha.desempilhar());
		assertEquals(20, pilha.desempilhar());
		assertEquals(10, pilha.desempilhar());
	}
	

	

}
