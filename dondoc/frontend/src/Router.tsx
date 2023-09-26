import { Route, Routes } from "react-router-dom";
import HomePage from './pages/webmain/Home/Home'
import SigninPage from './pages/webmain/Signin/Signin'
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
import AccountInfoPage from "./pages/webmain/AccountInfo";
import DetailMain from './pages/moimdetail/DetailMain/DetailMain'
import DetailFirst from './pages/moimdetail/DetailFirst/DetailFirst'
import DetailSecond from './pages/moimdetail/DetailSecond/DetailSecond'
import DetailThird from './pages/moimdetail/DetailThird/DetailThird'
import RequestModal from './pages/moimdetail/DetailFirst/RequestModal/RequestModal'
import SendMoneyFirstPage from "./pages/webmain/SendMoneyFirst";
import SendMoneySecondPage from "./pages/webmain/SendMoneySecond";
import SuccessPage from "./pages/webmain/SuccessPage";
import ErrorPage from "./pages/webmain/ErrorPage";
import SignUpFirstPage from "./pages/webmain/SignUpFirst";
import SignUpSecondPage from "./pages/webmain/SignUpSecond";


function Router() {

    return (
        <Routes>
            
            <Route path="/signin" element={<SigninPage/>}/>
            <Route path="/signupfirst" element={<SignUpFirstPage/>}/>
            <Route path="/signupsecond" element={<SignUpSecondPage/>}/>
            
     
            <Route path="/signupTemp" element={<SignupTempPage/>}/>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/accountInfo" element={<AccountInfoPage/>}/>
            <Route path="/sendmoneyfirst" element={<SendMoneyFirstPage/>}/>
            <Route path="/sendmoneysecond" element={<SendMoneySecondPage/>}/>
            <Route path="/error" element={<ErrorPage/>}/>
            <Route path="/success" element={<SuccessPage/>}/>
            

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
            <Route path="/detailmain" element={<DetailMain/>}/>
            <Route path="/detailfirst" element={<DetailFirst/>}/>
            <Route path="/detailsecond" element={<DetailSecond/>}/>
            <Route path="/detailthird" element={<DetailThird/>}/>
            <Route path="/requestmodal" element={<RequestModal/>}/>
            
            
        </Routes>
    )
}

export default Router