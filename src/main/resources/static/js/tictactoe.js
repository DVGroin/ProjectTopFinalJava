document.addEventListener("DOMContentLoaded", () => {
    let board = ["", "", "", "", "", "", "", "", ""];
    let currentPlayer = "X";
    let isGameActive = true;

    const statusDisplay = document.getElementById('statusDisplay');
    const cells = document.querySelectorAll('.tic-box');

    // Все возможные выигрышные комбинации на поле 3х3
    const winningConditions = [, [3, 4, 5], [6, 7, 8], // По горизонтали, [1, 4, 7], [2, 5, 8], // По вертикали, [2, 4, 6]             // По диагонали
    ];

    // Функция обработки клика по ячейке
    window.makeMove = function(cell) {
        const index = cell.getAttribute('data-index');

        if (board[index] !== "" || !isGameActive) {
            return;
        }

        board[index] = currentPlayer;
        cell.innerText = currentPlayer;

        // Добавляем стили неонового цвета в зависимости от игрока
        if (currentPlayer === "X") {
            cell.style.color = "var(--accent-mint)";
            cell.style.textShadow = "0 0 10px var(--accent-glow)";
        } else {
            cell.style.color = "var(--accent-cyan)";
            cell.style.textShadow = "0 0 10px rgba(6, 182, 212, 0.5)";
        }

        checkResult();
    };

    // Проверка результатов хода
    function checkResult() {
        let roundWon = false;

        for (let i = 0; i < winningConditions.length; i++) {
            const [a, b, c] = winningConditions[i];
            if (board[a] && board[a] === board[b] && board[a] === board[c]) {
                roundWon = true;
                break;
            }
        }

        if (roundWon) {
            statusDisplay.innerText = `Победитель: Игрок ${currentPlayer}! 🎉`;
            statusDisplay.style.color = "var(--accent-mint)";
            isGameActive = false;
            return;
        }

        if (!board.includes("")) {
            statusDisplay.innerText = "Ничья! 🤝";
            statusDisplay.style.color = "var(--accent-cyan)";
            isGameActive = false;
            return;
        }

        // Переключение игрока
        currentPlayer = currentPlayer === "X" ? "O" : "X";
        statusDisplay.innerText = `Ход игрока ${currentPlayer}`;
        statusDisplay.style.color = currentPlayer === "X" ? "var(--accent-mint)" : "var(--accent-cyan)";
    }

    // Сброс игры к начальному состоянию
    window.resetGame = function() {
        board = ["", "", "", "", "", "", "", "", ""];
        currentPlayer = "X";
        isGameActive = true;

        statusDisplay.innerText = "Ход игрока X";
        statusDisplay.style.color = "var(--accent-mint)";

        cells.forEach(cell => {
            cell.innerText = "";
            cell.style.color = "";
            cell.style.textShadow = "";
        });
    };
});
