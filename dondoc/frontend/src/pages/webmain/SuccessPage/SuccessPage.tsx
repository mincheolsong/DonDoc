import styles from "./SuccessPage.module.css";
import { useNavigate } from "react-router-dom";
import success from "../../../assets/image/target.svg"

function SuccessPage() {
  const navigate = useNavigate();
  return (
    <div style={{height:"100vh", display:"flex",justifyContent:"center",alignItems:"center"}}>
      <div className={styles.etcPages}>
        <img style={{marginTop:"25%"}} src={success} alt="" />
        <p style={{fontSize:"2rem",fontWeight:"bold",marginTop:"20%"}}>성공적으로 이체되었습니다.</p>
        <button onClick={()=>{
          navigate('/')
        }} className={styles.confirmBtn}>확인</button>
      </div>

    </div>
  );
}

export default SuccessPage;
