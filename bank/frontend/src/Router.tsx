import { Route, Routes } from "react-router-dom";
import HomePage from './pages/Home'
import AccountPage from './pages/API/CreateAccount'
// import LoginPage from './pages/LogIn'
import AccountNameSearchPage from './pages/API/AccountNameSearch'
import AccountListPage from './pages/API/AccountList' 
import AccountDetailPage from './pages/API/AccountDetail' 
import AccountTransPage from './pages/API/AccountTrans' 
import AccountTransDetailPage from './pages/API/AccountTransDetail' 
import AccountNamePage from './pages/API/AccountName' 
import AccountTransferPage from './pages/API/AccountTransfer' 
import AccountMasterPage from './pages/API/AccountMaster' 
import PasswordResetPage from './pages/API/PasswordReset'
// import MyApiPage from './pages/MyApi'

function Router() {

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            {/* <Route path="/login" element={<LoginPage/>}/> */}
            <Route path="/account-master" element={<AccountMasterPage/>}/>
            <Route path="/password-reset" element={<PasswordResetPage/>}/>
            <Route path="/account" element={<AccountPage/>}/>
            <Route path="/account-name-search" element={<AccountNameSearchPage/>}/>
            <Route path="/account-list" element={<AccountListPage/>}/>
            <Route path="/account-detail" element={<AccountDetailPage/>}/>
            <Route path="/account-trans" element={<AccountTransPage/>}/>
            <Route path="/account-trans-detail" element={<AccountTransDetailPage/>}/>
            <Route path="/account-name" element={<AccountNamePage/>}/>
            <Route path="/account-transfer" element={<AccountTransferPage/>}/>

            {/* <Route path="/myapi" element={<MyApiPage/>}></Route> */}
        </Routes>
    )
}

export default Router