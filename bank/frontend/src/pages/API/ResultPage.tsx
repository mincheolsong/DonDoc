import React from 'react'
import styles from './Account.module.css'
import { useLocation } from 'react-router-dom'

function ResultPage() {
  const location = useLocation()
  const state = location.state
    
    
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>이체 결과</div>
          </div>
  
          <div className={styles.contentbox}>
            {state.message}
          </div>
  
        </div>
      </div>
    )
}

export default ResultPage