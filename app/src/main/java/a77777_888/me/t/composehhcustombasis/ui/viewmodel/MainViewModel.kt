package a77777_888.me.t.composehhcustombasis.ui.viewmodel

import a77777_888.me.t.domain.dataentities.IDataProvider
import a77777_888.me.t.domain.dataentities.IFavorites
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import a77777_888.me.t.domain.dataentities.areas.Areas
import a77777_888.me.t.domain.dataentities.employer.EmployerResponseEntity
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancy.VacancyResponseEntity
import a77777_888.me.t.domain.models.BackendException
import a77777_888.me.t.domain.models.ConnectionException
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import a77777_888.me.t.domain.models.ParseBackendResponseException
import a77777_888.me.t.domain.usecases.AreasUseCase
import a77777_888.me.t.domain.usecases.EmployerUseCase
import a77777_888.me.t.domain.usecases.FavoritesInterActor
import a77777_888.me.t.domain.usecases.VacanciesUseCase
import a77777_888.me.t.domain.usecases.VacancyUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.Pager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty

@HiltViewModel
class MainViewModel @Inject constructor(
    val searchSettings: IFlowableSearchSettings,
    private val dataProvider: IDataProvider,
    iFavorites: IFavorites
) : ViewModel() {

    val uiState = UiState()

// VacanciesList
    var vacanciesListPagerFlow: Flow<Pager<Int, VacanciesListItem>> =
        VacanciesUseCase.getVacanciesPagerFlow(dataProvider, searchSettings)

// Areas
    private val _areasStateFlow = MutableStateFlow(DataLoadState<List<Areas>>())
    val areasStateFlow = _areasStateFlow.asStateFlow()

    fun loadAreas() =
        getData(
            _areasStateFlow,
            viewModelScope
        ) { AreasUseCase.getAreas(dataProvider) }

// vacancy
    private val _vacancyStateFlow = MutableStateFlow(DataLoadState<VacancyResponseEntity>())
    val vacancyStateFlow = _vacancyStateFlow.asStateFlow()

    fun loadVacancy(id: String, scope: CoroutineScope) =
        getData(
            _vacancyStateFlow,
            scope
        ) { VacancyUseCase.getVacancy(dataProvider, id) }


// employer
    private val _employerStateFlow = MutableStateFlow(DataLoadState<EmployerResponseEntity>())
    val employerStateFlow = _employerStateFlow.asStateFlow()

    fun loadEmployer(id: String, scope: CoroutineScope) =
        getData(
            _employerStateFlow,
            scope
        ) { EmployerUseCase.getEmployer(dataProvider, id) }


// favorites
    private val favoritesInterActor = FavoritesInterActor(iFavorites)
    val favoritesListFlow = favoritesInterActor.getFavorites().distinctUntilChanged()
    fun addFavorite(favorite: IVacanciesListItem, scope: CoroutineScope) = scope.launch(Dispatchers.IO) {
        favoritesInterActor.addFavorite(favorite)
    }

    fun deleteFavorite(favorite: IVacanciesListItem, scope: CoroutineScope) = scope.launch(Dispatchers.IO) {
        favoritesInterActor.deleteFavorite(favorite)
    }

    private fun <T> getData(
        stateFlow: MutableStateFlow<DataLoadState<T>>,
        scope: CoroutineScope,
        block: suspend () -> T
    ) = scope.launch(Dispatchers.IO) {
        val state = DataLoadState<T>()
        stateFlow.value = state

        try {
            stateFlow.update { state.copy(content = block.invoke()) }
        } catch (e: ParseBackendResponseException) {
            stateFlow.update { state.copy(error = e.text) }
        } catch (e: ConnectionException) {
            stateFlow.update { state.copy(error = e.text) }
        } catch (e: BackendException) {
            stateFlow.update {
                state.copy(error = "${e.text}:\\nКод ${e.code}\\n${e.message}")
            }
        } catch (e: Exception) {
            stateFlow.update { state.copy(error = "") }
        }
    }

}

data class DataLoadState<T>(
    val content: T? = null,
    val error: String? = null
)

class UiState {
    var masterNavController: NavHostController? = null
    var detailsNavController: NavHostController? = null
    private var vacancyId: String = ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = vacancyId
    operator fun setValue(thisRef: Any?, property: KProperty<*>, s: String) {
        vacancyId = s
    }
}





