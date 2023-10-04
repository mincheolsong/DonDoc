import styles from "./ErrorPage.module.css";
import Error from "../../../assets/image/error.svg"
import { useLocation, useNavigate } from "react-router-dom";

function ErrorPage() {
  const navigate = useNavigate();
  const {state} = useLocation();
  return (
    <div style={{height:"100vh", display:"flex",justifyContent:"center",alignItems:"center"}}>
      <div className={styles.etcPages}>
        <img style={{marginTop:"15%"}} src={Error} alt="" />
        <p style={{fontSize:"2rem",fontWeight:"bold"}}>이체에 실패했습니다.</p>
        <p style={{fontSize:"1.5rem",fontWeight:"bold"}}>실패사유 : {state}</p>
        <button onClick={()=>{
          navigate('/')
        }} className={styles.confirmBtn}>돌아가기</button>
      </div>

    </div>
  );
}

export default ErrorPage;
