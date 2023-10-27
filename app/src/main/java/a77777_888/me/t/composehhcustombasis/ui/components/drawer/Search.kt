package a77777_888.me.t.composehhcustombasis.ui.components.drawer

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.theme.AppTypography
import a77777_888.me.t.data.repositories.AreasRepository
import a77777_888.me.t.data.repositories.getNameById
import a77777_888.me.t.domain.dataentities.areas.startWith
import a77777_888.me.t.domain.models.IFlowableSearchSettings
import a77777_888.me.t.domain.models.ISearchSettings
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXPERIENCE_1_3
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXPERIENCE_3_6
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXPERIENCE_MORE_THEN_6
import a77777_888.me.t.domain.models.ISearchSettings.Companion.EXPERIENCE_NO_EXPERIENCE
import a77777_888.me.t.domain.models.copyTo
import a77777_888.me.t.domain.models.searchmodels.FindIn
import a77777_888.me.t.domain.models.searchmodels.Schedule
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.take

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class
)
@Composable
fun Search(
    fABOnClickEvent: MutableState<Boolean>,
    settings: IFlowableSearchSettings
) {


    var findWords by remember { mutableStateOf(
        settings.findWords ?:""
    ) }
    var excludeWords by remember { mutableStateOf(
        settings.excludeWords ?:""
    ) }

    var findInName by remember { mutableStateOf(
        settings.findIn.name
    ) }
    var findInDescription by remember { mutableStateOf(
        settings.findIn.description
    ) }
    var findInCompanyName by remember { mutableStateOf(
        settings.findIn.companyName
    ) }

    var areaId by remember { mutableStateOf(
        ""
    ) }

    var experience by remember { mutableStateOf(
        settings.experience ?:""
    ) }

    var remoteSchedule by remember { mutableStateOf(
        settings.schedule.remote
    ) }
    var fullDaySchedule by remember { mutableStateOf(
        settings.schedule.fullDay
    ) }
    var shiftSchedule by remember { mutableStateOf(
        settings.schedule.shift
    ) }
    var flexibleSchedule by remember { mutableStateOf(
        settings.schedule.flexible
    ) }
    var flyInFlyOutSchedule by remember { mutableStateOf(
        settings.schedule.flyInFlyOut
    ) }

    var period by remember { mutableStateOf(
        settings.period ?:""
    ) }

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current

    fun saveState() {
        val newSetting = object : ISearchSettings {
            override var findWords: String? = findWords.ifBlank { null }
            override var excludeWords: String? = excludeWords.ifBlank { null }
            override var findIn: FindIn =
                FindIn(name = findInName, description = findInDescription, companyName = findInCompanyName)
            override var regionId: String? = areaId.ifBlank { null }
            override var experience: String? = experience.ifBlank { null }
            override var schedule: Schedule =
                Schedule(
                    remote = remoteSchedule,
                    fullDay = fullDaySchedule,
                    shift = shiftSchedule,
                    flexible = flexibleSchedule,
                    flyInFlyOut = flyInFlyOutSchedule
                )
            override var period: String? = period.ifBlank { null }
        }

        newSetting.copyTo(settings)
        settings.saveSettings()
        settings.emitSettings(newSetting)
    }

    LaunchedEffect(key1 = fABOnClickEvent.value) {
        if (fABOnClickEvent.value) {
            saveState()
            fABOnClickEvent.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
//====================================   find && exclude   ==========================================
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            value = findWords,
            onValueChange = { findWords = it },
            label = { Text(stringResource(id = R.string.find)) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                IconButton(onClick = {findWords  = "" }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            value = excludeWords,
            onValueChange = { excludeWords = it },
            label = { Text(stringResource(id = R.string.exclude)) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                IconButton(onClick = { excludeWords = "" }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
//====================================   findIn   ===================================================
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = findInName,
                onClick = { findInName = !findInName; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.in_name)) },
                leadingIcon = {
                    if (findInName)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = findInDescription,
                onClick = { findInDescription = !findInDescription; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.in_description)) },
                leadingIcon = {
                    if (findInDescription)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = findInCompanyName,
                onClick = { findInCompanyName = !findInCompanyName; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.in_company_name)) },
                leadingIcon = {
                    if (findInCompanyName)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )

        }
//====================================   Region   ===================================================

        var region by remember {
            mutableStateOf(
                settings.regionId?.let { AreasRepository.areas!!.getNameById(settings.regionId!!) } ?: ""
            )
        }

        val inputFlow = MutableStateFlow("")
        var expanded by remember { mutableStateOf(true) }

        val relevantRegions = inputFlow
            .debounce(300)
            .filter {
                it.length > 1
            }
            .take(5)
            .mapLatest { prefix ->
                AreasRepository.areas
                    ?.startWith(prefix, 7)
                    ?: listOf()
            }
            .collectAsStateWithLifecycle(initialValue = listOf())

        ExposedDropdownMenuBox(
            expanded = expanded && relevantRegions.value.isNotEmpty(), //expanded,
            onExpandedChange = {
            }
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                value = region,
                onValueChange = {region = it; inputFlow.value = it },
                label = { Text(stringResource(id = R.string.region)) },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                supportingText = { Text(text = stringResource(R.string.enter_first_characters)) },
                trailingIcon = {
                    IconButton(onClick = { region = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
            )

            ExposedDropdownMenu(
                expanded = expanded && relevantRegions.value.isNotEmpty(),
                onDismissRequest = {
                // expanded = false;
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
            ) {
                relevantRegions.value.forEach {
                    DropdownMenuItem(
                        onClick = {
                            region = it.name
                            areaId = it.id
                            expanded = false
                            focusManager.clearFocus(force = true)
                        },
                    ) {
                        Text(
                            text = it.name,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.medium
                            )
                        )
//                        FilterChip(selected = false, onClick = {}, label = { Text(text = it.name) })

                    }
                }

            }
        }
//====================================   Experience   ===============================================
        Text(
            text = stringResource(R.string.experience),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.outline,
            style = AppTypography.titleMedium
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = experience == "",
                onClick = { experience = ""; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.not_matter)) },
                leadingIcon = {
                    if (experience == "")
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = experience == EXPERIENCE_NO_EXPERIENCE,
                onClick = { experience =  EXPERIENCE_NO_EXPERIENCE; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.no_experience)) },
                leadingIcon = {
                    if (experience == EXPERIENCE_NO_EXPERIENCE)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = experience == EXPERIENCE_1_3,
                onClick = { experience =  EXPERIENCE_1_3; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.between1And3)) },
                leadingIcon = {
                    if (experience == EXPERIENCE_1_3)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = experience == EXPERIENCE_3_6,
                onClick = { experience =  EXPERIENCE_3_6; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.between3And6)) },
                leadingIcon = {
                    if (experience == EXPERIENCE_3_6)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = experience == EXPERIENCE_MORE_THEN_6,
                onClick = { experience =  EXPERIENCE_MORE_THEN_6; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.moreThan6)) },
                leadingIcon = {
                    if (experience == EXPERIENCE_MORE_THEN_6)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
        }
//====================================   Schedule   ===========================================
        Text(
            text = stringResource(R.string.schedule),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.outline,
            style = AppTypography.titleMedium
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = remoteSchedule,
                onClick = {remoteSchedule  = !remoteSchedule; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.remote_schedule)) },
                leadingIcon = {
                    if (remoteSchedule)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = fullDaySchedule,
                onClick = {fullDaySchedule  = !fullDaySchedule; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.full_day_schedule)) },
                leadingIcon = {
                    if (fullDaySchedule)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = shiftSchedule,
                onClick = {shiftSchedule  = !shiftSchedule; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.shift_schedule)) },
                leadingIcon = {
                    if (shiftSchedule)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = flexibleSchedule,
                onClick = {flexibleSchedule  = !flexibleSchedule; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.flexibleSchedule_schedule)) },
                leadingIcon = {
                    if (flexibleSchedule)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
            FilterChip(
                selected = flyInFlyOutSchedule,
                onClick = {flyInFlyOutSchedule  = !flyInFlyOutSchedule; focusManager.clearFocus(force = true) },
                label = { Text(text = stringResource(id = R.string.fly_in_fly_out_schedule)) },
                leadingIcon = {
                    if (flyInFlyOutSchedule)
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                }
            )
        }
//====================================   Period   ===================================================
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            value = period,
            onValueChange = { period = it },
            label = { Text(stringResource(id = R.string.period)) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                IconButton(onClick = {period  = "" }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )

        Spacer(modifier = Modifier.height(96.dp))

    }

}

