import { Route, Routes } from "react-router-dom";
import HomePage from './pages/Home'
import AccountPage from './pages/ApiInfo'
import LoginPage from './pages/LogIn'
import MyApiPage from './pages/MyApi'

function Router() {

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}></Route>
            <Route path="/account" element={<AccountPage/>}></Route>
            <Route path="/login" element={<LoginPage/>}></Route>
            <Route path="/myapi" element={<MyApiPage/>}></Route>
        </Routes>
    )
}

export default Router