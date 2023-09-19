import { Route, Routes } from "react-router-dom";
import HomePage from './pages/webmain/Home/Home'
import SigninPage from './pages/webmain/Signin/Signin'
import SignupPage from './pages/webmain/Signup/Signup'
import SearchPage from './pages/search/Search/Search' 
import MypagePage from './pages/mypage/Mypage/Mypage' 
import SettingPage from './pages/mypage/Setting/Setting' 
import ChangePasswordPage from './pages/mypage/ChangePassword/ChangePassword' 
import AccountListPage from './pages/mypage/AccountList/AccountList' 
import ChangeCharacterPage from './pages/mypage/ChangeCharacter/ChangeCharacter' 
import DiffProfilePage from './pages/mypage/DiffProfile/DiffProfile' 
import FriendListPage from './pages/mypage/FriendList/FriendList'
import MoimHomePage from './pages/moim/MoimHome/MoimHome'
import CreateMoimPage from './pages/moim/CreateMoim/CreateMoim'
import MoimLinkAccountPage from './pages/moim/MoimLinkAccount/MoimLinkAccount'
import MoimSelectAccountPage from './pages/moim/MoimSelectAccount/MoimSelectAccount'
import CreateResultPage from './pages/moim/CreateResult/CreateResult'
import MoimJoinPage from './pages/moim/MoimJoin/MoimJoin'
import MissionPage from './pages/mission/Mission/Mission'
import SignupTempPage from "./pages/webmain/SignupTemp";
function Router() {

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/signin" element={<SigninPage/>}/>
            <Route path="/signup" element={<SignupPage/>}/>
            <Route path="/signupTemp" element={<SignupTempPage/>}/>
            <Route path="/search" element={<SearchPage/>}/>
            <Route path="/mypage" element={<MypagePage/>}/>
            <Route path="/setting" element={<SettingPage/>}/>
            <Route path="/changepassword" element={<ChangePasswordPage/>}/>
            <Route path="/accountlist" element={<AccountListPage/>}/>
            <Route path="/changecharacter" element={<ChangeCharacterPage/>}/>
            <Route path="/diffprofile" element={<DiffProfilePage/>}/>
            <Route path="/friendlist" element={<FriendListPage/>}/>
            <Route path="/moimhome" element={<MoimHomePage/>}/>
            <Route path="/createmoim" element={<CreateMoimPage/>}/>
            <Route path="/moimlink" element={<MoimLinkAccountPage/>}/>
            <Route path="/moimselect" element={<MoimSelectAccountPage/>}/>
            <Route path="/createresult" element={<CreateResultPage/>}/>
            <Route path="/moimjoin" element={<MoimJoinPage/>}/>
            <Route path="/mission" element={<MissionPage/>}/>
            
        </Routes>
    )
}

export default Router