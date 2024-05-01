package com.example.spruce

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CardTravel
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MonochromePhotos
import androidx.compose.material.icons.rounded.Nature
import androidx.compose.material.icons.rounded.PermMedia
import androidx.compose.material.icons.rounded.SevereCold
import androidx.compose.material.icons.rounded.SubdirectoryArrowRight
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material.icons.rounded.Water
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spruce.models.PostModel
import com.example.spruce.repos.DataManager
import com.example.spruce.repos.Resource
import com.example.spruce.screens.CategoryScreen
import com.example.spruce.screens.DrawerMenu
import com.example.spruce.screens.HomeScreen
import com.example.spruce.screens.NavigationRoutes
import com.example.spruce.screens.PostDetailScreen
import com.example.spruce.screens.PostsScreen
import com.example.spruce.ui.theme.SpruceTheme
import com.example.spruce.ui.theme.SpruceThemeColor
import com.example.spruce.ui.theme.fonts
import com.example.spruce.viewModels.CategoryViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpruceTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationSetup()
                }
            }
        }
    }
}

@Composable
fun NavigationSetup(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) {

    val viewModel: CategoryViewModel = hiltViewModel()
    val categories = viewModel.categoryFlow.collectAsState()

    val menus by remember {
        mutableStateOf(
            arrayListOf(
                DrawerMenu(101, Icons.Rounded.Home, "Home", NavigationRoutes.Home.name),
                DrawerMenu(101, Icons.Rounded.PermMedia, "Posts", NavigationRoutes.Post.name),
                DrawerMenu(101, Icons.Rounded.Category, "Category", NavigationRoutes.Category.name)
            )
        )
    }

    categories.value?.let {
        when (it) {
            is Resource.Failure -> {
                Log.d(
                    "categoryResponse",
                    "NavigationSetup: CategoryFailure - ${it.exception.message}"
                )
            }

            Resource.Loading -> {

            }

            is Resource.Success -> {
                Log.d("categoryResponse", "NavigationSetup: CategorySuccess - ${it.result}")
                val tempList = it.result

                for (categoryModel in tempList) {
                    menus.add(
                        DrawerMenu(
                            categoryModel.id,
                            DataManager.findIcon(categoryModel.slug),
                            categoryModel.name,
                            NavigationRoutes.Category.name,
                            Modifier.padding(start = 20.dp)
                        )
                    )
                }
            }
        }
    }

    var icon: ImageVector by remember {
        mutableStateOf(menus[0].icon)
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus = menus) { route, itIcon, id, title ->
                    scope.launch {
                        drawerState.close()
                    }
                    icon = itIcon
                    if ((route != NavigationRoutes.Category.name && id == 101) || (route == NavigationRoutes.Category.name && id != 101))
                        navController.navigate(route = if (route == NavigationRoutes.Category.name) "${route}/$id/$title" else route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                                inclusive = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                }
            }
        },
    ) {

        val lazyListState: LazyListState = rememberLazyListState()

        Scaffold(topBar = {
            CustomTopAppBar(menuIcon = {
                Icon(imageVector = icon, contentDescription = "Side Drawer Icon")
            }, lazyListState) {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        }) {

            val host = NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Home.name,
                modifier = Modifier.padding(it)
            ) {

                composable(route = NavigationRoutes.Home.name) {
                    HomeScreen(lazyListState = lazyListState) { postData ->
                        DataManager.postModel = postData
                        navController.navigate(NavigationRoutes.PostDetails.name)
                    }
                }

                composable(route = NavigationRoutes.Post.name) {
                    PostsScreen { postData ->
                        DataManager.postModel = postData
                        navController.navigate(NavigationRoutes.PostDetails.name)
                    }
                }

                composable(route = "${NavigationRoutes.Category.name}/{id}/{title}") { navBackStackEntry ->
                    val catId = navBackStackEntry.arguments?.getString("id")!!
                    val catTitle = navBackStackEntry.arguments?.getString("title")!!
                    CategoryScreen(catId.toInt(), title = catTitle) { postData ->
                        DataManager.postModel = postData
                        navController.navigate(NavigationRoutes.PostDetails.name)
                    }
                }

                composable(route = NavigationRoutes.PostDetails.name) {
                    PostDetailScreen(
                        postModel = DataManager.postModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    menuIcon: @Composable () -> Unit,
    lazyListState: LazyListState,
    onMenuClicked: () -> Unit
) {
    val size by animateIntAsState(
        targetValue = if (0 != lazyListState.firstVisibleItemIndex) {
            30
        } else {
            40
        },
        label = "dpAnimation",
    )

    CenterAlignedTopAppBar(
        title = {
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Spruce",
                    fontFamily = fonts,
                    fontWeight = FontWeight.Black,
                    fontSize = size.sp
                )
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .size((size - 30).coerceAtLeast(6).dp)
                            .border(
                                width = (size - 38.8).coerceAtLeast(0.5).dp,
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                            .padding((size - 38.8).coerceAtLeast(0.5).dp)
                            .clip(CircleShape)
                            .background(color = SpruceThemeColor())

                    )
                    Spacer(modifier = Modifier.width((size - 20.0).coerceAtLeast(16.0).dp))
                }

            }
        },

        navigationIcon = {
            IconButton(onClick = {
                onMenuClicked()
            }) {
                menuIcon()
            }
        }

    )
}

@Composable
private fun DrawerContent(
    menus: ArrayList<DrawerMenu>,
    onMenuClick: (String, ImageVector, Int, String) -> Unit
) {
    var selected by remember {
        mutableIntStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Spruce",
                fontFamily = fonts,
                fontWeight = FontWeight.Black,
                fontSize = 40.sp
            )
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 2.5.dp)
                        .size(10.dp)
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .padding(1.dp)
                        .clip(CircleShape)
                        .background(color = SpruceThemeColor())

                )
                Spacer(modifier = Modifier.width(23.dp))
            }

        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(menus) {

                NavigationDrawerItem(
                    label = { Text(text = it.title) },
                    icon = {
                        if (menus.indexOf(it) > 2) {
                            Icon(
                                imageVector = Icons.Rounded.SubdirectoryArrowRight,
                                contentDescription = ""
                            )
                        }
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null,
                            modifier = it.modifier
                        )
                    },
                    selected = menus.indexOf(it) == selected,
                    onClick = {
                        onMenuClick(it.route, it.icon, it.id, it.title)
                        selected = menus.indexOf(it)
                    }
                )

                if (menus.indexOf(it) < 3)
                    HorizontalDivider(thickness = 1.dp)
            }

        }
    }
}