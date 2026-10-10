const boardSize = 10;
const minesCount = 15;
let boardData = [];
let revealedCount = 0;
const boardElement = document.getElementById("minesweeper-board");

function initMinesweeper() {
    boardElement.innerHTML = "";
    boardData = [];
    revealedCount = 0;

    // Генерируем сетку данных
    for (let r = 0; r < boardSize; r++) {
        let row = [];
        for (let c = 0; c < boardSize; c++) {
            row.push({ isMine: false, isRevealed: false, count: 0, r, c });
        }
        boardData.push(row);
    }

    // Расставляем мины случайно
    let placedMines = 0;
    while (placedMines < minesCount) {
        let r = Math.floor(Math.random() * boardSize);
        let c = Math.floor(Math.random() * boardSize);
        if (!boardData[r][c].isMine) {
            boardData[r][c].isMine = true;
            placedMines++;
        }
    }

    // Считаем цифры вокруг мин
    for (let r = 0; r < boardSize; r++) {
        for (let c = 0; c < boardSize; c++) {
            if (boardData[r][c].isMine) continue;
            let count = 0;
            for (let dr = -1; dr <= 1; dr++) {
                for (let dc = -1; dc <= 1; dc++) {
                    let nr = r + dr, nc = c + dc;
                    if (nr >= 0 && nr < boardSize && nc >= 0 && nc < boardSize && boardData[nr][nc].isMine) {
                        count++;
                    }
                }
            }
            boardData[r][c].count = count;
        }
    }

    // Отрисовываем DOM элементы ячеек
    for (let r = 0; r < boardSize; r++) {
        for (let c = 0; c < boardSize; c++) {
            let cellElement = document.createElement("div");
            cellElement.classList.add("cell");
            cellElement.id = `cell-${r}-${c}`;
            cellElement.addEventListener("click", () => revealCell(r, c));
            boardElement.appendChild(cellElement);
        }
    }
}

function revealCell(r, c) {
    let cell = boardData[r][c];
    if (cell.isRevealed) return;

    let cellElement = document.getElementById(`cell-${r}-${c}`);
    cell.isRevealed = true;
    cellElement.classList.add("revealed");

    if (cell.isMine) {
        cellElement.classList.add("mine");
        cellElement.innerText = "💣";
        alert("Бум! Вы подорвались на мине. 💥");
        initMinesweeper();
        return;
    }

    revealedCount++;
    if (cell.count > 0) {
        cellElement.innerText = cell.count;
    } else {
        // Каскадное открытие пустых ячеек
        for (let dr = -1; dr <= 1; dr++) {
            for (let dc = -1; dc <= 1; dc++) {
                let nr = r + dr, nc = c + dc;
                if (nr >= 0 && nr < boardSize && nc >= 0 && nc < boardSize) {
                    revealCell(nr, nc);
                }
            }
        }
    }

    if (revealedCount === (boardSize * boardSize) - minesCount) {
        alert("Поздравляем! Вы успешно разминировали поле! 🏆");
        initMinesweeper();
    }
}

// Запуск при инициализации страницы
initMinesweeper();
