// import React from 'react'
import styles from './Home.module.css'

function Home() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.title}>서비스 소개</div>
        <div className={styles.information}>DD Bank에서 제공하는 서비스 소개 입니다</div>
      </div>


    </div>
  )
}

export default Home