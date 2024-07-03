package ru.hits.studentintership.presentation.positions.model

enum class PositionStatus {
    DOESNT_DO_ANYTHING,
    ARRANGED_INTERVIEW,
    PASSED_INTERVIEW,
    RECEIVED_OFFER,
    ACCEPTED_OFFER,
    NOT_RECEIVED_OFFER,
    REJECTED_OFFER,
    CONFIRMED_RECEIVED_OFFER,
}

internal fun PositionStatus.toUi(): String {
    return when (this) {
        PositionStatus.DOESNT_DO_ANYTHING -> "Ничего не делаю"
        PositionStatus.ARRANGED_INTERVIEW -> "Договорился о собеседовании"
        PositionStatus.PASSED_INTERVIEW -> "Прошел собеседование"
        PositionStatus.RECEIVED_OFFER -> "Прислали оффер"
        PositionStatus.ACCEPTED_OFFER -> "Принял оффер"
        PositionStatus.NOT_RECEIVED_OFFER -> "Не прислали оффер"
        PositionStatus.REJECTED_OFFER -> "Отклонил оффер"
        PositionStatus.CONFIRMED_RECEIVED_OFFER -> "Подтвердили присланный оффер"
    }
}

internal fun String.toPositionStatusEnum(): PositionStatus =
    when (this) {
        "DOESNT_DO_ANYTHING" -> PositionStatus.DOESNT_DO_ANYTHING
        "ARRANGED_INTERVIEW" -> PositionStatus.ARRANGED_INTERVIEW
        "PASSED_INTERVIEW" -> PositionStatus.PASSED_INTERVIEW
        "RECEIVED_OFFER" -> PositionStatus.RECEIVED_OFFER
        "ACCEPTED_OFFER" -> PositionStatus.ACCEPTED_OFFER
        "NOT_RECEIVED_OFFER" -> PositionStatus.NOT_RECEIVED_OFFER
        "REJECTED_OFFER" -> PositionStatus.REJECTED_OFFER
        "CONFIRMED_RECEIVED_OFFER" -> PositionStatus.CONFIRMED_RECEIVED_OFFER
        else -> PositionStatus.DOESNT_DO_ANYTHING
    }