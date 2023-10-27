package a77777_888.me.t.domain.usecases

import a77777_888.me.t.domain.dataentities.IDataProvider

class EmployerUseCase {
    companion object {
        suspend fun getEmployer(dataProvider: IDataProvider, id: String) =
            dataProvider.getEmployer(id)
    }
}