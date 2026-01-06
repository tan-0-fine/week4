package com.example.navigation.data

import com.example.navigation.model.OnboardingItem
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.navigation.R


val onboardingList = listOf(
    OnboardingItem(
        title = "Easy Time Management",
        description = "With management based on priority and\n daily tasks, it will give you convenience in\n managing and determining the tasks that\n must be done first ",
        imageRes = R.drawable.onboarding1
    ),

    OnboardingItem(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination\n of more important tasks will give your job\n statistics better and always improve",
        imageRes = R.drawable.onboarding2
    ),

    OnboardingItem(
        title = "Reminder Notification",
        description = "The advantage of this application is\n that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set.",
        imageRes = R.drawable.onboarding3
    ),
)