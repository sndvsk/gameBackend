let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#play").prop("disabled", false);
    } else {
        $("#play").prop("disabled", true);
    }
}

function connect() {
    let socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        subscribeToWinnings();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function subscribeToWinnings() {
    stompClient.subscribe('/topic/winnings', function (winnings) {
        let response = JSON.parse(winnings.body);
        let winningsDisplay;
        if (response.won) {
            // Check if winnings are an integer
            if (Number.isInteger(response.winnings)) {
                // If winnings are an integer, display as is
                winningsDisplay = response.winnings;
            } else {
                // If winnings are not an integer, display with 2 decimal places
                winningsDisplay = response.winnings.toFixed(2);
            }
            document.getElementById('message').innerHTML = `You won: ${winningsDisplay}`;
        } else {
            document.getElementById('message').innerHTML = 'Sorry, you lost.';
        }
    });
}

function playGame() {
    let playData = validateInputs();

    let fixedBet = playData.bet.toFixed(2);

    if (playData.valid) {
        let player = { 'bet': fixedBet, 'number': playData.number };
        stompClient.send("/app/play", {}, JSON.stringify(player));
    }
}

function validateInput(input, errorEmpty, errorInvalid, errorRange) {
    if (input.value === "") {
        input.classList.add('is-invalid');
        errorEmpty.style.display = 'block';
        errorInvalid.style.display = 'none';
        errorRange.style.display = 'none';
        return false;
    } else if (isNaN(input.value) || input.value < 0) {
        input.classList.add('is-invalid');
        errorEmpty.style.display = 'none';
        errorInvalid.style.display = 'block';
        errorRange.style.display = 'none';
        return false;
    } else if (
        (input.id === 'bet' && (input.value < 1 || input.value > 2147483647)) ||
        (input.id === 'number' && (input.value < 1 || input.value > 99))) {
        input.classList.add('is-invalid');
        errorEmpty.style.display = 'none';
        errorInvalid.style.display = 'none';
        errorRange.style.display = 'block';
        return false;
    } else {
        input.classList.remove('is-invalid');
        errorEmpty.style.display = 'none';
        errorInvalid.style.display = 'none';
        errorRange.style.display = 'none';
        return true;
    }
}

function validateInputs() {
    let betInput = document.getElementById('bet');
    let numberInput = document.getElementById('number');
    let betErrorEmpty = document.getElementById('bet-error-empty');
    let betErrorInvalid = document.getElementById('bet-error-invalid');
    let betErrorRange = document.getElementById('bet-error-range');
    let numberErrorEmpty = document.getElementById('number-error-empty');
    let numberErrorInvalid = document.getElementById('number-error-invalid');
    let numberErrorRange = document.getElementById('number-error-range');

    let isBetValid = validateInput(betInput, betErrorEmpty, betErrorInvalid, betErrorRange);
    let isNumberValid = validateInput(numberInput, numberErrorEmpty, numberErrorInvalid, numberErrorRange);

    if (isBetValid && isNumberValid) {
        let form = document.getElementById('game-form');
        if (form.checkValidity()) {
            return { valid: true, bet: parseFloat(betInput.value), number: parseInt(numberInput.value) };
        } else {
            form.classList.add('was-validated');
        }
    } else {
        if (!isBetValid) {
            betInput.classList.add('is-invalid');
        }
        if (!isNumberValid) {
            numberInput.classList.add('is-invalid');
        }
    }

    return { valid: false };
}

$(function () {
    setConnected(false);

    $("#connect").click(function () { connect(); });
    $("#disconnect").click(function () { disconnect(); });
    $("#play").click(function () { playGame(); });
});
