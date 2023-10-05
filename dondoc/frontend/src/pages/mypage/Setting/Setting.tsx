import styles from "./Setting.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import { useNavigate } from "react-router-dom";
import {LuLogOut} from "react-icons/lu"
import { moim } from "../../../api/api";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useDispatch } from "react-redux";
import { logoutUser } from "../../../store/slice/userSlice";

function Setting() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  return (
    <div>
      <BackLogoHeader name="설정" left="15%" fontSize="2rem" top="2.3%"/>
        <div className={styles.mainBox} >
          <div className={styles.settingItem} onClick={()=>{
            navigate('/changepassword')
          }}>
            비밀번호 변경
          </div>

          <div onClick={()=>{
            moim.get("/api/user/logout",{headers:{Authorization:`Bearer ${userInfo.accessToken}`}})
            .then(()=>{
              dispatch(logoutUser())
            }).catch((err)=>{
              console.log(err)
            }).finally(()=>{
              navigate("/")
            })

          }} className={styles.logoutIcon}>
            <p>로그아웃</p>&nbsp; <LuLogOut />
          </div>
        </div>
    </div>
  );
};

export default Setting;
