@file:Suppress("SpellCheckingInspection")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.app.masterdog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.masterdog.ui.theme.MasterDogTheme
import com.app.masterdog.ui.theme.MasterPurple
import com.app.masterdog.ui.theme.MasterPurpleDark
import com.app.masterdog.ui.theme.MasterPurpleLight
import com.app.masterdog.ui.theme.MasterSuccess
import com.app.masterdog.ui.theme.MasterTeal
import com.app.masterdog.ui.theme.MasterWhite
import com.app.masterdog.ui.theme.MasterYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(onBackToLogin: () -> Unit = {}) {
    val pets = remember {
        listOf(
            PetProfile("Luna", "Perro", "Golden Retriever", "4 años", "24 kg", "Polen", "Salud estable"),
            PetProfile("Griss", "Gato", "Siamés", "2 años", "5 kg", "Ninguna", "Control dental pendiente"),
            PetProfile("Nina", "Perro", "Beagle", "6 años", "13 kg", "Lactosa", "Tratamiento dermatológico")
        )
    }
    var selectedPetIndex by rememberSaveable { mutableIntStateOf(0) }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis mascotas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackToLogin) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (pets.isEmpty()) {
            EmptyPetsState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MasterPurpleLight)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            FilterChip(
                selected = selectedPetIndex == -1,
                onClick = {
                    selectedPetIndex = -1
                    selectedTab = 0
                },
                label = { Text("TODAS MIS MASCOTAS") },
                leadingIcon = { Icon(Icons.Default.Pets, contentDescription = null) }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                pets.forEachIndexed { index, pet ->
                    PetAvatarChip(
                        pet = pet,
                        selected = selectedPetIndex == index,
                        onClick = {
                            selectedPetIndex = index
                            selectedTab = 0
                        }
                    )
                }
            }

            if (selectedPetIndex == -1) {
                AllPetsActivitiesCard(pets = pets)
            } else {
                val selectedPet = pets[selectedPetIndex]
                PetSummaryCard(pet = selectedPet)
                PetTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                when (selectedTab) {
                    0 -> ProfileTab(selectedPet)
                    1 -> VisitsTab(selectedPet)
                    2 -> DocsTab(selectedPet)
                }
            }
        }
    }
}

@Composable
private fun PetAvatarChip(
    pet: PetProfile,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(if (selected) MasterWhite else Color.Transparent)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(if (selected) MasterYellow else MasterTeal.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pet.name.take(2).uppercase(),
                color = MasterPurpleDark,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(pet.name, fontWeight = FontWeight.SemiBold, color = MasterPurpleDark)
    }
}

@Composable
private fun PetSummaryCard(pet: PetProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MasterWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = CircleShape, color = MasterPurple) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = MasterWhite,
                    modifier = Modifier.padding(18.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                Text(pet.name, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = MasterPurpleDark)
                Text("${pet.species} - ${pet.breed}", color = MasterPurpleDark.copy(alpha = 0.8f))
                Text("${pet.age} | ${pet.weight}", color = MasterTeal, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PetTabs(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Perfil", "Visitas", "Docs")
    PrimaryTabRow (
        selectedTabIndex = selectedTab,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MasterWhite,
        contentColor = MasterPurple
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = { Text(title, fontWeight = FontWeight.SemiBold) }
            )
        }
    }
}

@Composable
private fun ProfileTab(pet: PetProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MasterWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Resumen clínico", color = MasterPurpleDark, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                ProfileMetric("Edad", pet.age, Modifier.weight(1f))
                ProfileMetric("Peso", pet.weight, Modifier.weight(1f))
            }

            InfoRow("Especie", pet.species)
            InfoRow("Raza", pet.breed)
            InfoRow("Alergias", pet.allergies)

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MasterTeal.copy(alpha = 0.12f)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Estado de salud", color = MasterTeal, fontWeight = FontWeight.Bold)
                    Text(pet.healthStatus, color = MasterPurpleDark, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun VisitsTab(pet: PetProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MasterWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, contentDescription = null, tint = MasterTeal)
                    Text("Calendario de ${pet.name}", color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
                }

                CalendarGrid()
                CalendarLegend()
            }
        }

        ReminderCard(message = "Recordatorio: aplicar antiparasitario y confirmar asistencia a la próxima cita.")
    }
}

@Composable
private fun DocsTab(pet: PetProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MasterWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Documentos de ${pet.name}", color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
            DocumentRow("Carnet de vacunas.pdf", "Actualizado el 22/03/2026")
            DocumentRow("Resultados de laboratorio.pdf", "Hemograma y perfil bioquímico")
            DocumentRow("Indicaciones médicas.pdf", "Tratamiento y cuidados en casa")
        }
    }
}

@Composable
private fun AllPetsActivitiesCard(pets: List<PetProfile>) {
    val agendaItems = listOf(
        PetAgendaItem(9, pets[1].name, "Control dental", "10:00", MasterTeal),
        PetAgendaItem(10, pets[0].name, "Última visita", "11:30", MasterPurple),
        PetAgendaItem(12, pets[0].name, "Vacunación anual", "09:00", MasterSuccess),
        PetAgendaItem(13, pets[2].name, "Baño medicado", "16:00", MasterYellow),
        PetAgendaItem(14, pets[1].name, "Recordatorio antiparasitario", "Todo el día", MasterTeal)
    )

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MasterWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Pets, contentDescription = null, tint = MasterTeal)
                    Text("Agenda de todas mis mascotas", color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
                }

                AllPetsCalendarGrid(agendaItems)
                AllPetsLegend(agendaItems)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MasterWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Actividades próximas", color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
                agendaItems.forEach { item ->
                    AgendaActivityRow(item)
                }
            }
        }
    }
}

@Composable
private fun AllPetsCalendarGrid(items: List<PetAgendaItem>) {
    val days = (8..14).toList()
    val labels = listOf("L", "M", "M", "J", "V", "S", "D")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            labels.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    color = MasterPurpleDark.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            days.forEach { day ->
                val dayItems = items.filter { it.day == day }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (dayItems.isEmpty()) MasterPurpleLight else MasterWhite)
                        .border(1.dp, MasterPurple.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(day.toString(), color = MasterPurpleDark, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        dayItems.take(3).forEach { item ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(item.color)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AllPetsLegend(items: List<PetAgendaItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items
            .distinctBy { it.petName }
            .forEach { item ->
                CalendarLegendItem(color = item.color, text = "${item.petName}: citas y actividades")
            }
    }
}

@Composable
private fun AgendaActivityRow(item: PetAgendaItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MasterPurple.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(item.color)
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(item.petName, color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
            Text(item.activity, color = MasterPurpleDark.copy(alpha = 0.78f), fontSize = 13.sp)
        }
        Text("Día ${item.day}\n${item.time}", color = MasterTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
    }
}

@Composable
private fun ProfileMetric(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MasterPurpleLight.copy(alpha = 0.75f)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, color = MasterPurpleDark.copy(alpha = 0.65f), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(value, color = MasterPurpleDark, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MasterPurple.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MasterPurpleDark.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold)
        Text(value, color = MasterPurpleDark, fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
    }
}

@Composable
private fun CalendarGrid() {
    val days = (8..14).toList()
    val labels = listOf("L", "M", "M", "J", "V", "S", "D")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            labels.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    color = MasterPurpleDark.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            days.forEach { day ->
                val color = when (day) {
                    12 -> MasterSuccess
                    10 -> MasterTeal
                    else -> MasterPurpleLight
                }
                val textColor = if (day == 12 || day == 10) MasterWhite else MasterPurpleDark
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(day.toString(), color = textColor, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CalendarLegend() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CalendarLegendItem(color = MasterSuccess, text = "Próxima cita: Vacunación anual - 12/05/2026")
        CalendarLegendItem(color = MasterTeal, text = "Última visita: Control general - 10/04/2026")
    }
}

@Composable
private fun CalendarLegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(text, color = MasterPurpleDark.copy(alpha = 0.82f), fontSize = 13.sp)
    }
}

@Composable
private fun ReminderCard(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MasterYellow.copy(alpha = 0.32f)
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(14.dp),
            color = MasterPurpleDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun DocumentRow(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MasterPurple.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Description, contentDescription = null, tint = MasterTeal)
            Text(title, color = MasterPurpleDark, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider(color = MasterPurple.copy(alpha = 0.1f))
        Text(subtitle, color = MasterPurpleDark.copy(alpha = 0.7f), fontSize = 13.sp)
    }
}

@Composable
private fun EmptyPetsState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MasterPurpleLight)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Pets, contentDescription = null, tint = MasterPurple, modifier = Modifier.size(72.dp))
        Spacer(Modifier.height(16.dp))
        Text("Aún no tienes mascotas registradas", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MasterPurpleDark)
        Text(
            "Agrega tu primera mascota para ver su perfil, visitas y documentos.",
            textAlign = TextAlign.Center,
            color = MasterPurpleDark.copy(alpha = 0.75f),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MasterSuccess),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("Agregar mi primera mascota", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

private data class PetProfile(
    val name: String,
    val species: String,
    val breed: String,
    val age: String,
    val weight: String,
    val allergies: String,
    val healthStatus: String
)

private data class PetAgendaItem(
    val day: Int,
    val petName: String,
    val activity: String,
    val time: String,
    val color: Color
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PetsScreenPreview() {
    MasterDogTheme(dynamicColor = false) {
        PetsScreen()
    }
}

