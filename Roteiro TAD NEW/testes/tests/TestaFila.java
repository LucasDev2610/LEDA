package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import tad.fila.FilaCheiaException;
import tad.fila.FilaIF;
import tad.fila.FilaVaziaException;
import tad.fila.MinhaFila;

public class TestaFila {

	private FilaIF<Integer> fila = null;

	@BeforeEach
	public void iniciar() {
		fila = new MinhaFila();
	}

	@Test
	public void enfileirarTest() {
		try {
			fila.enfileirar(3);
			assertEquals( 3, fila.verificarCabeca());
			fila.enfileirar(5);
			assertEquals( 3, fila.verificarCabeca());
			fila.enfileirar(7);
			assertEquals( 3, fila.verificarCabeca());
			fila.enfileirar(4);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(2);
			assertEquals(3, fila.verificarCabeca());
		} catch(FilaCheiaException fce) {
			fail("fila cheia exception lançado indevidamente");
		}
	}

	@Test
	public void verificarCabecaTest() {
		assertNull(fila.verificarCabeca());
		try {
			fila.enfileirar(3);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(5);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(7);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(4);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(2);
			assertEquals(3, fila.verificarCabeca());
		}catch (FilaCheiaException fce) {
			fail("fila cheia exception lançado indevidamente");
		}

	}

	// configurar o tamanho da fila para 5
	@Test
	public void desenfileirarTest() {
		fila = new MinhaFila(5);
		Exception e = assertThrows(FilaVaziaException.class,
                () -> {fila.desenfileirar();});

        String expectedMessage = "fila vazia!";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

		try {
			fila.enfileirar(3);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(5);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(7);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(4);
			assertEquals(3, fila.verificarCabeca());
			fila.enfileirar(2);
			assertEquals(3, fila.verificarCabeca());
			assertEquals(3, fila.desenfileirar());
			assertEquals(3, fila.verificarCabeca());
	
			fila.enfileirar(15);
			
			assertEquals(5, fila.desenfileirar());
			assertEquals(7, fila.verificarCabeca());
	
			fila.enfileirar(20);
			
			assertEquals(7, fila.desenfileirar());
			assertEquals(4, fila.verificarCabeca());
	
			assertEquals(4, fila.desenfileirar());
			assertEquals(2, fila.verificarCabeca());
	
			assertEquals(2, fila.desenfileirar());
		} catch(FilaCheiaException fce) {
			fail("fila cheia exception lançado indevidamente");
		} catch(FilaVaziaException fve) {
			fail("fila vazia exception lançado indevidamente");
		}
	}

	@Test
	public void isEmptyTest() {
		try {
			assertTrue(fila.isEmpty());
			fila.enfileirar(3);
			assertEquals(3, fila.verificarCabeca());
			assertFalse(fila.isEmpty());
			fila.desenfileirar();
			assertTrue(fila.isEmpty());
		} catch(FilaCheiaException fce) {
			fail("fila cheia exception lançado indevidamente");
		} catch(FilaVaziaException fve) {
			fail("fila vazia exception lançado indevidamente");
		}
	}
	
	@Test
	public void filaVaziaTest() {
		assertThrows(FilaVaziaException.class, () -> {
			fila.enfileirar(3);
			fila.enfileirar(2);
			fila.enfileirar(10);
			fila.desenfileirar();
			fila.desenfileirar();
			fila.desenfileirar();
			fila.desenfileirar();
	    });
	}
	
	@Test
	public void filaCheiaTest() {
		assertThrows(FilaCheiaException.class, () -> {
			fila.enfileirar(3);
			fila.enfileirar(2);
			fila.enfileirar(10);
			fila.enfileirar(3);
			fila.enfileirar(2);
			fila.enfileirar(10);
			fila.enfileirar(5);
			fila.enfileirar(7);
			fila.enfileirar(10);
			fila.enfileirar(10);
			fila.enfileirar(11);
	    });
	}
	
	// Neste teste a fila tem que estourar o tamanho depois de 999
	@Test
	public void enfileirarEstouroExceptionTest() {
		fila = new MinhaFila(1000);
		assertThrows(FilaCheiaException.class, () -> {
			for (int i = 0; i <= 1000; i++) { // 1001 elementos para capacidade 1000
				fila.enfileirar(i);
			}
		});
	}

	@Test
	public void estadoIntermediarioTest() throws Exception {
		fila.enfileirar(1);
		fila.enfileirar(2);
		fila.desenfileirar();
		assertEquals(2, fila.verificarCabeca());
		assertFalse(fila.isEmpty());
	}

	// ========== TESTES DE CAPACIDADE (Ponto 3) ========== //

	@Test
	public void capacidadePadrao_DeveSerCinco() {
		assertEquals(5, fila.capacidade(), "A capacidade padrão deve ser 5");
	}

	@Test
	public void capacidadeCustomizada_DeveRespeitarTamanhoInformado() {
		FilaIF<Integer> filaCustom = new MinhaFila(100);
		assertEquals(100, filaCustom.capacidade(), "Deve respeitar a capacidade informada no construtor");
	}

	@Test
	public void tamanhoAtual_DeveRefletirQuantidadeElementos() throws FilaCheiaException {
		assertEquals(0, fila.tamanho(), "Tamanho inicial deve ser zero");

		fila.enfileirar(1);
		assertEquals(1, fila.tamanho(), "Deve incrementar tamanho ao enfileirar");

		fila.enfileirar(2);
		assertEquals(2, fila.tamanho(), "Deve incrementar tamanho ao enfileirar");
	}

	// ========== TESTES COM NOMES MELHORADOS (Ponto 4) ========== //

	@Test
	public void enfileirar_QuandoFilaVazia_DeveTornarElementoCabeca() throws FilaCheiaException {
		fila.enfileirar(10);
		assertEquals(10, fila.verificarCabeca(), "Primeiro elemento deve se tornar cabeça");
	}

	@Test
	public void enfileirar_QuandoFilaNaoVazia_DeveManterCabecaOriginal() throws FilaCheiaException {
		fila.enfileirar(10);
		fila.enfileirar(20);
		assertEquals(10, fila.verificarCabeca(), "Cabeça deve permanecer a mesma após novas inserções");
	}

	@Test
	public void desenfileirar_QuandoFilaVazia_DeveLancarFilaVaziaException() {
		assertThrows(FilaVaziaException.class, () -> fila.desenfileirar(),
				"Deveria lançar exceção ao desenfileirar fila vazia");
	}

	@Test
	public void desenfileirar_QuandoFilaNaoVazia_DeveRetornarElementoMaisAntigo() throws FilaCheiaException, FilaVaziaException {
		fila.enfileirar(10);
		fila.enfileirar(20);
		fila.enfileirar(30);

		assertEquals(10, fila.desenfileirar(), "Deve remover o elemento mais antigo");
		assertEquals(20, fila.desenfileirar(), "Deve remover o próximo elemento mais antigo");
	}

	@Test
	public void verificarCabeca_QuandoFilaVazia_DeveRetornarNull() {
		assertNull(fila.verificarCabeca(), "Cabeça deve ser null para fila vazia");
	}

	@Test
	public void isEmpty_QuandoFilaVazia_DeveRetornarTrue() {
		assertTrue(fila.isEmpty(), "Deveria retornar true para fila vazia");
	}

	@Test
	public void isEmpty_QuandoFilaNaoVazia_DeveRetornarFalse() throws FilaCheiaException {
		fila.enfileirar(10);
		assertFalse(fila.isEmpty(), "Deveria retornar false para fila não vazia");
	}

	@Test
	public void enfileirar_AlemDaCapacidade_DeveLancarFilaCheiaException() {
		FilaIF<Integer> filaPequena = new MinhaFila(3);

		assertThrows(FilaCheiaException.class, () -> {
			filaPequena.enfileirar(1);
			filaPequena.enfileirar(2);
			filaPequena.enfileirar(3);
			filaPequena.enfileirar(4); // Deve estourar aqui
		}, "Deveria lançar exceção ao exceder capacidade");
	}

	@Test
	public void comportamentoFila_DeveSeguirOrdemFIFO() throws FilaCheiaException, FilaVaziaException {
		// Given - Configuração inicial
		fila.enfileirar(10);
		fila.enfileirar(20);
		fila.enfileirar(30);

		// When/Then - Verificações
		assertEquals(10, fila.desenfileirar(), "Primeiro a entrar deve ser primeiro a sair");
		assertEquals(20, fila.desenfileirar(), "Segundo a entrar deve ser segundo a sair");
		assertEquals(30, fila.desenfileirar(), "Terceiro a entrar deve ser terceiro a sair");
	}
}
