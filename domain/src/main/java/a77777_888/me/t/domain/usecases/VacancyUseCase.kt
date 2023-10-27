package a77777_888.me.t.domain.usecases

import a77777_888.me.t.domain.dataentities.IDataProvider

class VacancyUseCase {
    companion object {
        suspend fun getVacancy(dataProvider: IDataProvider, id: String) =
            dataProvider.getVacancy(id)
    }
}