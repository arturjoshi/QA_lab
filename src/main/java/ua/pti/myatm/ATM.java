package ua.pti.myatm;

public class ATM {
    private double money;
    private Card card;

    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM) {
        if (moneyInATM < 0) {
            throw new IllegalArgumentException();
        }
        this.card = null;
        this.money = moneyInATM;
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
        return money;
    }

    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false.
    // При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) {
        if (card == null) {
            throw new NullPointerException();
        } else if (!card.checkPin(pinCode) || card.isBlocked()) {
            return false;
        } else {
            this.card = card;
            return true;
        }
    }

    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NoCardException {
        isInserted();
        return card.getAccount().getBalance();
    }

    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NoCardException, NotEnoughMoneyInAccountException, NotEnoughMoneyInATMException {
        isInserted();
        Account account = this.card.getAccount();
        if (account.getBalance() < amount) {
            throw new NotEnoughMoneyInAccountException();
        } else if (this.getMoneyInATM() < amount) {
            throw new NotEnoughMoneyInATMException();
        } else {
            this.money -= account.withdrow(amount);
            return account.getBalance();
        }
    }

    public void isInserted() throws NoCardException {
        if (this.card == null) {
            throw new NoCardException();
        }
    }
}
