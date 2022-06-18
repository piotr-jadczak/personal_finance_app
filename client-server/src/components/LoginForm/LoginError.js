
const LoginError = ({ message }) => {
  return (
    <div className="alert alert-danger mt-2" role="alert">
      <a>{message}</a>
    </div>
  )
}

export default LoginError