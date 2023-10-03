import styles from "./Setting.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import { useNavigate } from "react-router-dom";


function Setting() {
  const navigate = useNavigate();

  return (
    <div>
      <BackLogoHeader name="설정" left="15%" fontSize="2rem" top="2.3%"/>
        <div className={styles.mainBox} >
          <div className={styles.settingItem} onClick={()=>{
            navigate('/changepassword')
          }}>
            비밀번호 변경
          </div>
        </div>
    </div>
  );
};

export default Setting;
