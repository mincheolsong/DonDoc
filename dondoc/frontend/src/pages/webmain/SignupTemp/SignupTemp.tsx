import styles from "./SignupTemp.module.css";
import { useNavigate } from "react-router-dom";


function SignupTemp() {
  const navigate = useNavigate();
  return (
    <>
     <div style={{display:"flex",height:"100vh", alignItems:"center",justifyContent:"center"}}>
      <div className={styles.container}>
        <div>
          <p  style={{fontSize:"2.4rem", textAlign:"center",fontWeight:"normal"}}>
        DonDoc 회원가입을<br /> 환영합니다.
          </p>
        </div>
        <div>
          <p style={{fontSize:"1.6rem", textAlign:"center",color:"#87898E"}}>
          회원가입 해주셔서 감사합니다.
          <br />
          DonDoc 만의 강력한 계좌 관리기능을 통해 편리해진 자산관리를 경험해 보세요.
          </p>
          </div>
        <div style={{width:"95%",display:"flex", flexDirection:"column", justifyContent:"end",marginTop:"2em"}}>
        <button onClick={()=>{
          navigate('/callaccount')
        }} className={styles.confirmBtn}>확인</button>
        </div>
      </div>
     </div>
    </>
  );
}

export default SignupTemp;
