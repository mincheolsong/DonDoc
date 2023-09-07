import { BrowserRouter, Route, Routes, Link, Switch } from "react-router-dom";
import HomePage from './pages/Home'
import AccountPage from './pages/ApiInfo'
import LoginPage from './pages/LogIn'
import MyApiPage from './pages/MyApi'

function Router() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HomePage/>}></Route>
                <Route path="/account" element={<AccountPage/>}></Route>
                <Route path="/login" element={<LoginPage/>}></Route>
                <Route path="/myapi" element={<MyApiPage/>}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default Router