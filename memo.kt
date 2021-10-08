1. Send back stack from A screen
requestSendMessage.observe(viewLifecycleOwner) {
    navController.previousBackStackEntry?.savedStateHandle?.set(KEY_BACK_STACK, it)
    navController.popBackStack()
}

2. Receive result from previous screen
private fun observeNavigateCallBack() {
    navController.currentBackStackEntry?.savedStateHandle
        ?.getLiveData<Pair<Long, DriversMessageTemplateId>>(MessageTemplateFragment.KEY_BACK_STACK)
        ?.observe(viewLifecycleOwner) {
            navController.currentBackStackEntry?.savedStateHandle
                ?.remove<Pair<Long, DriversMessageTemplateId>>(MessageTemplateFragment.KEY_BACK_STACK)
            val id = it.first
            val messageId = it.second
            messageTimeLineViewModel.patchMessage(id, messageId)
        }
}


