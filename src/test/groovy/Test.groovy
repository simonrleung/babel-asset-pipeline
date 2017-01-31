import asset.pipeline.babel.BabelProcessor


class Test {
    static main(args) {

        def code = '''
            class HelloMessage extends React.Component {
              render() {
                return <div>Hello {this.props.name}</div>;
              }
            }

'''
        BabelProcessor processor = new BabelProcessor()
        println processor.process(code, null)

    }
}
