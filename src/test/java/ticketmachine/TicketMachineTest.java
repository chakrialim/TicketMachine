package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	
	@Test 
	// S3 : on n’imprime pas leticket si le montant inséré est insuffisant
	void ticketNotPrintedIfInsufficientFunds() {
		machine.insertMoney(PRICE - 10);
		assertFalse(machine.printTicket(), "Le ticket a été imprimé malgré des fonds insuffisants");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void ticketPrintedIfSufficientFunds() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé malgré des fonds suffisants");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceDecrementedWhenTicketPrinted() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(20, machine.getBalance(), "La balance n'est pas correctement décrémentée après impression du ticket");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalUpdatedWhenTicketPrinted() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le montant total collecté n'est pas mis à jour après impression du ticket");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundReturnsCorrectBalance() {
		machine.insertMoney(30);
		machine.insertMoney(20);
		assertEquals(50, machine.refund(), "refund() ne rend pas le bon montant");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundResetsBalance() {
		machine.insertMoney(20);
		machine.refund();
		assertEquals(0, machine.getBalance(), "refund() ne remet pas la balance à zéro");
	}

	@Test
	// S9 : on ne peut pas insérerun montant négatif
	void insertNegativeAmountThrowsException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			machine.insertMoney(-100);
		});
		String expectedMessage = "Montant doit etre positif";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage), "Le message d'exception est incorrect");
	}

	@Test
	// S10 :  on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void createMachineWithNegativePriceThrowsException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-50);
		});
		String expectedMessage = "Ticket price must be positive";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage), "Le message d'exception est incorrect");
	}



}
